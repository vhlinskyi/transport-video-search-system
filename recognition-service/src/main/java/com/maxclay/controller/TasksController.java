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
import java.security.Principal;

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

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public ResponseEntity createTask(Principal principal, @RequestBody(required = false) JsonNode task) {

        Task created = taskService.createTask(principal.getName());
        log.info("Task '{}' created", created);
        return ResponseEntity.ok(created);
    }

    @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.GET)
    public ResponseEntity getTask(Principal principal, @PathVariable String taskId) {

        Task fetched = taskService.getById(principal.getName(), taskId);
        return ResponseEntity.ok(fetched);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ResponseEntity getAllTasks(Principal principal) {
        return ResponseEntity.ok(Lists.reverse(taskService.getAllByOwner(principal.getName())));
    }

    @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.POST)
    public ResponseEntity uploadFile(Principal principal, @PathVariable String taskId, MultipartFile file) throws IOException {

        taskService.attachFileToTask(file, principal.getName(), taskId);
        log.debug("File '{}' attached to the task with id = '{}'", file.getOriginalFilename(), taskId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/tasks/{taskId}/process", method = RequestMethod.POST)
    public ResponseEntity processTask(Principal principal, @PathVariable String taskId) {

        Task task = taskService.process(principal.getName(), taskId);
        log.info("Processing task '{}'", task);
        return ResponseEntity.ok(task);
    }

}
