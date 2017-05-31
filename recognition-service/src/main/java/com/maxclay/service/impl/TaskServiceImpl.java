package com.maxclay.service.impl;

import com.maxclay.config.TaskFilesUploadProperties;
import com.maxclay.exception.ResourceNotFoundException;
import com.maxclay.exception.ValidationException;
import com.maxclay.model.Task;
import com.maxclay.repository.TaskRepository;
import com.maxclay.service.RecognitionService;
import com.maxclay.service.TaskService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Vlad Glinskiy
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final static Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;
    private final Resource filesUploadDir;
    private final RecognitionService recognitionService;
    private final ExecutorService executorService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           TaskFilesUploadProperties taskFilesUploadProperties,
                           RecognitionService recognitionService) {

        this.taskRepository = taskRepository;
        this.filesUploadDir = taskFilesUploadProperties.getUploadPath();
        this.recognitionService = recognitionService;
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public Task createTask() {
        return taskRepository.save(new Task());
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
//            String filePath = new FileSystemResource(tempFile).getPath();
            String filePath = tempFile.getAbsolutePath();
            log.info("File for task with id = '{}' persisted at '{}'", taskId, filePath);

            if (isImage(file)) {
                task.addImage(filePath);
            } else {
                task.addVideo(filePath);
            }

            taskRepository.save(task);
        }
    }

    @Override
    public void process(String taskId) {

        Task task = taskRepository.findOne(taskId);
        executorService.execute(() -> {
            processImages(task);
            processVideos(task);
        });
    }

    private void processImages(Task task) {

        task.getImages().stream()
                .map(FileSystemResource::new)
                .map(resource -> {
                    byte[] imageData = null;
                    try {
                        imageData = org.apache.commons.io.IOUtils.toByteArray(resource.getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return imageData;
                })
                .filter(Objects::nonNull)
                .forEach(recognitionService::recognize);
    }

    private void processVideos(Task task) {
        task.getVideos().stream()
                .map(FilenameFrameSequence::new)
                .forEach(frameSequence -> {
                    while (frameSequence.hasNext()) {
                        byte[] imageData = frameSequence.getNext();
                        if (imageData != null) {
                            recognitionService.recognize(imageData);
                        }
                    }
                });
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
