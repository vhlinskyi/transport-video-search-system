package com.maxclay.repository;

import com.maxclay.model.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public interface AlertRepository extends MongoRepository<Alert, String> {

    List<Alert> getAllByOwner(String owner);
}
