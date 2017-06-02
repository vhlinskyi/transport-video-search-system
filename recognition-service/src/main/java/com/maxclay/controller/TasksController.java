package com.maxclay.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.maxclay.model.Task;
import com.maxclay.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Vlad Glinskiy
 */
@RestController
public class TasksController {

    private static final Logger log = LoggerFactory.getLogger(TasksController.class);

    private final TaskService taskService;

    @Autowired
    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    // TODO create task only for authenticated users
    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public ResponseEntity createTask(@RequestBody(required = false) JsonNode task) {

        Task created = taskService.createTask();
        log.info("Task '{}' created", created);
        return ResponseEntity.ok(created);
    }

    // TODO create task only for authenticated users
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ResponseEntity getAllTasks() {
        return ResponseEntity.ok(Lists.reverse(taskService.getAll()));
    }

    // TODO add files only for authenticated users
    @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.POST)
    public ResponseEntity uploadFile(@PathVariable String taskId, MultipartFile file) throws IOException {

        taskService.attachFileToTask(file, taskId);
        log.info("File '{}' attached to the task with id = '{}'", file.getOriginalFilename(), taskId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // TODO process tasks only for authenticated users, check for the task owner
    @RequestMapping(value = "/tasks/{taskId}/process", method = RequestMethod.POST)
    public ResponseEntity processTask(@PathVariable String taskId) {

        Task task = taskService.process(taskId);
        log.info("Processing task '{}'", task);
        return ResponseEntity.ok(task);
    }

}
