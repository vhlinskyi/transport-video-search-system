package com.maxclay.service;

import com.maxclay.model.Task;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * TODO create separate service?
 *
 * @author Vlad Glinskiy
 */
public interface TaskService {

    Task createTask();

    void attachFileToTask(MultipartFile file, String taskId) throws IOException;

    void process(String taskId);

}
