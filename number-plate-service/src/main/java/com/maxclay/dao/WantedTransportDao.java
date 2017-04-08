package com.maxclay.dao;

import com.maxclay.model.WantedTransport;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vlad Glinskiy
 */
public interface WantedTransportDao extends MongoRepository<WantedTransport, String>, WantedTransportOperations {
}
