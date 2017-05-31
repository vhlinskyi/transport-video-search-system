package com.maxclay.repository;

import com.maxclay.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vlad Glinskiy
 */
public interface TaskRepository extends MongoRepository<Task, String> {
}
