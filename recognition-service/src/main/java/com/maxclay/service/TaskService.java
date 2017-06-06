package com.maxclay.service;

import com.maxclay.model.SearchResult;
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

    Task createTask(String owner);

    Task getById(String owner, String taskId);

    List<Task> getAllByOwner(String owner);

    void attachFileToTask(MultipartFile file, String owner, String taskId) throws IOException;

    Task process(String owner, String taskId);

}
