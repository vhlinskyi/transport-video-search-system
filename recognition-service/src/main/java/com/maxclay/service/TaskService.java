package com.maxclay.service;

import com.maxclay.model.Task;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * TODO create separate service?
 *
 * @author Vlad Glinskiy
 */
public interface TaskService {

    Task createTask();

    Task getById(String taskId);

    List<Task> getAll();

    void attachFileToTask(MultipartFile file, String taskId) throws IOException;

    Task process(String taskId);

}
