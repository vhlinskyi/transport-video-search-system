package com.maxclay.service;

import com.maxclay.dto.UserDto;
import com.maxclay.model.User;

import java.util.List;

/**
 * Service, which provides methods that allow to perform manage {@link com.maxclay.model.User} instances.
 *
 * @author Vlad Glinskiy
 */
public interface UserService {

    /**
     * Retrieves authenticated user. Note that if there is no authenticated user,
     * {@link com.maxclay.exception.ResourceNotFoundException} will be thrown.
     *
     * @return authenticated user.
     */
    User getAuthenticatedUser();

    /**
     * Retrieves user by his identifier. Note that if there is no user with specified identifier,
     * {@link com.maxclay.exception.ResourceNotFoundException} will be thrown.
     *
     * @param id user's identifier.
     * @return user, whose identifier corresponds to the specified one.
     */
    User get(String id);

    /**
     * Retrieves list of all users.
     *
     * @return list of all users.
     */
    List<User> getAll();

    /**
     * Retrieves user by his email address. Note that if there is no user with specified email address,
     * {@link com.maxclay.exception.ResourceNotFoundException} will be thrown.
     *
     * @param email user's email.
     * @return user, whose identifier corresponds to the specified one.
     */
    User getByEmail(String email);

    /**
     * Registers new user to the system according to the given user's data transfer object. Note, that data transfer
     * object must contain valid values and all required fields must be set('name', 'email', 'password' and
     * 'matchingPassword'), in other case {@link com.maxclay.exception.ValidationException} will be thrown.
     *
     * @param userDto user's data transfer object, which contains information required for registration.
     * @return registered user.
     */
    User register(UserDto userDto);

    /**
     * Saves specified user. If the given user exists, he will be updated, in other case new user will be saved.
     *
     * @param user user, which will be saved.
     * @return saved user.
     */
    User save(User user);

    /**
     * Deletes specified user.
     *
     * @param user user, which will be deleted.
     */
    void delete(User user);

    /**
     * Deletes user according to his identifier.
     *
     * @param id user's identifier.
     */
    void delete(String id);

    /**
     * Checks whether user with the specified identifier exists or not.
     *
     * @param id user's identifier.
     * @return 'true' if user with the specified identifier exists, 'false' otherwise.
     */
    boolean exists(String id);

    /**
     * Checks whether specified email address available or not.
     *
     * @param email email address.
     * @return 'true' if the address is available, 'false' if some user with the specified address already exists.
     */
    boolean emailAvailable(String email);
}
