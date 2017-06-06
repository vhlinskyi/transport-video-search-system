package com.maxclay.controller;

import com.maxclay.model.SearchResult;
import com.maxclay.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Vlad Glinskiy
 */
@RestController
public class RecognitionActivityController {

    private final TaskService taskService;

    @Autowired
    public RecognitionActivityController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/suspicious", method = RequestMethod.GET)
    public ResponseEntity getSuspiciousByUser(Principal principal) {

        List<SearchResult> suspicious = taskService.getAllByOwner(principal.getName()).stream()
                .flatMap(task -> task.getSuspicious().stream())
                .filter(distinctByKey(SearchResult::getWantedTransport))
                .collect(Collectors.toList());
        return ResponseEntity.ok(suspicious);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
