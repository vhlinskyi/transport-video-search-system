package com.maxclay.repository;

import com.maxclay.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vlad Glinskiy
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
}
