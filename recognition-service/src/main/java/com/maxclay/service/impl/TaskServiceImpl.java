package com.maxclay.service.impl;

import com.maxclay.client.NumberPlateSearchService;
import com.maxclay.config.FilesUploadProperties;
import com.maxclay.controller.TasksWebSocketHandler;
import com.maxclay.exception.ResourceNotFoundException;
import com.maxclay.exception.ValidationException;
import com.maxclay.model.*;
import com.maxclay.repository.TaskRepository;
import com.maxclay.service.RecognitionService;
import com.maxclay.service.TaskService;
import com.maxclay.service.VideoService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO review it. Too much logic concentrated here.
 *
 * @author Vlad Glinskiy
 */
@Service
public class TaskServiceImpl implements TaskService {

    public static final int DEFAULT_FRAME_STEP_IN_SECONDS = 1;

    //FIXME
    public static final String DEFAULT_IMAGE_EXTENSION = ".png";

    // added just for testing. should be changed
    private static final int INDEX_OF_SINGLE_RECOGNITION_RESULT = 0;
    private static final int INDEX_OF_SINGLE_WANTED_TRANSPORT = 0;

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;
    private final Resource filesUploadDir;
    private final Resource suspiciousPicturesDir;
    private final RecognitionService recognitionService;
    private final ExecutorService executorService;
    private final TasksWebSocketHandler tasksWebSocketHandler;
    private final VideoService videoService;
    private final NumberPlateSearchService plateSearchService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           FilesUploadProperties filesUploadProperties,
                           RecognitionService recognitionService,
                           TasksWebSocketHandler tasksWebSocketHandler,
                           VideoService videoService,
                           NumberPlateSearchService plateSearchService) {

        this.taskRepository = taskRepository;
        this.filesUploadDir = filesUploadProperties.getTasksFilesPath();
        this.suspiciousPicturesDir = filesUploadProperties.getSuspiciousPicturesPath();
        this.recognitionService = recognitionService;
        this.executorService = Executors.newCachedThreadPool();
        this.tasksWebSocketHandler = tasksWebSocketHandler;
        this.videoService = videoService;
        this.plateSearchService = plateSearchService;
    }

    @Override
    public Task createTask() {
        return taskRepository.save(new Task());
    }

    @Override
    public Task getById(String taskId) {

        Task task = taskRepository.findOne(taskId);
        if (task == null) {
            throw new ResourceNotFoundException(String.format("Task with id = '%s' not found", taskId));
        }

        return task;
    }


    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public void attachFileToTask(MultipartFile file, String taskId) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new ValidationException("File can not be empty");
        }

        if (!isImage(file) && !isVideo(file)) {
            throw new ValidationException("File has wrong type. Must be either video or image");
        }

        if (taskId == null || taskId.isEmpty()) {
            throw new ValidationException("Task's identifier can not be empty");
        }

        Task task = taskRepository.findOne(taskId);
        if (task == null) {
            throw new ResourceNotFoundException(String.format("Task with id = '%s' not found", taskId));
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("pic", fileExtension, filesUploadDir.getFile());

        try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
            String filePath = tempFile.getAbsolutePath();
            log.debug("File for task with id = '{}' persisted at '{}'", taskId, filePath);

            if (isImage(file)) {
                task.addImage(filePath);
            } else {
                task.addVideo(filePath);
            }

            taskRepository.save(task);
        }
    }

    // TODO review it
    @Override
    public Task process(String taskId) {

        Task task = taskRepository.findOne(taskId);
        long imagesNumber = task.getImagesNumber();
        long framesNumber = task.getVideos().stream()
                .mapToLong(videoService::getDurationInSeconds)
                .sum() / DEFAULT_FRAME_STEP_IN_SECONDS;

        task.setApproximateSize(imagesNumber + framesNumber);
        taskRepository.save(task);

        executorService.execute(() -> {

            task.setProcessing(true);

            processImages(task);
            processVideos(task);

            task.setProcessing(false);
            task.setDone(true);
            taskRepository.save(task);
            tasksWebSocketHandler.sendTask(task);
        });

        return task;
    }

    private void processImages(Task task) {

        for (String imagePath : task.getImages()) {

            byte[] imageData = null;
            try {
                imageData = org.apache.commons.io.IOUtils.toByteArray(new FileSystemResource(imagePath).getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (imageData == null) {
                continue;
            }

            processBinaryImageData(task, imageData, getFileExtension(imagePath));
        }
    }

    private void processVideos(Task task) {
        task.getVideos().stream()
                .map(videoPath -> videoService.getSequence(videoPath, DEFAULT_FRAME_STEP_IN_SECONDS))
                .forEach(frameSequence -> {
                    while (frameSequence.hasNext()) {
                        processBinaryImageData(task, frameSequence.getNext(), DEFAULT_IMAGE_EXTENSION);
                    }
                });
    }

    private void processBinaryImageData(Task task, byte[] imageData, String extension) {
        RecognitionResult result = null;
        if (imageData != null) {
            result = recognitionService.recognize(imageData);
        }

        task.incrementProcessed();
        if (result != null && !result.isEmpty()) {
            PlateRecognitionResult recognitionResult = result.getPlateResults().get(INDEX_OF_SINGLE_RECOGNITION_RESULT);
            if (task.addRecognized(recognitionResult)) {
                // TODO change it to search multiple plates at once. added just for testing.
                PlateSearchRequest searchRequest = new PlateSearchRequest(recognitionResult.getPlateNumber());
                List<WantedTransport> searchResult = plateSearchService.search(Arrays.asList(searchRequest));
                if (!searchResult.isEmpty()) {
                    WantedTransport wantedTransport = searchResult.get(INDEX_OF_SINGLE_WANTED_TRANSPORT);
                    String imagePath = saveBinaryImageData(imageData, extension);
                    task.addSuspicious(new SearchResult(wantedTransport, imagePath));
                }
            }
        }
        tasksWebSocketHandler.sendTask(task);
    }

    // synchronize it?
    private String saveBinaryImageData(byte[] imageData, String extension) {

        String path = null;
        try {
            File tempFile = File.createTempFile("pic", extension, suspiciousPicturesDir.getFile());
            try (InputStream in = new ByteArrayInputStream(imageData); OutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(in, out);
            }
            path = tempFile.getName();
            log.info("Image of suspicious matches saved at: {}", path);
        } catch (Exception e) {
            log.warn("Cant save image for suspicious matches", e);
        }

        return path;
    }

    private boolean isImage(MultipartFile file) {
        return file.getContentType().startsWith("image");
    }

    private boolean isVideo(MultipartFile file) {
        return file.getContentType().startsWith("video");
    }

    private static String getFileExtension(String name) {
        return name.substring(name.lastIndexOf("."));
    }
}
