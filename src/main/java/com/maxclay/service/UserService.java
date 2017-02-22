package com.maxclay.service;

import com.maxclay.dto.UserDto;
import com.maxclay.model.User;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public interface UserService {

    User getAuthenticatedUser();

    User get(String id);

    List<User> getAll();

    User getByEmail(String email);

    User register(UserDto userDto);

    User save(User user);

    void delete(User user);

    void delete(String id);

    boolean exists(String id);

    boolean emailAvailable(String email);
}
