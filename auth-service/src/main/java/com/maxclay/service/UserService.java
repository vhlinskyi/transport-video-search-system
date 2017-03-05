package com.maxclay.service;

import com.maxclay.model.User;

/**
 * Service, which provides methods that allow to manage {@link com.maxclay.model.User} instances.
 *
 * @author Vlad Glinskiy
 */
public interface UserService {

    /**
     * Retrieves user by his identifier. Note that if there is no user with specified identifier,
     * {@link com.maxclay.exception.ResourceNotFoundException} unchecked exception will be thrown.
     *
     * @param id user's identifier.
     * @return user, whose identifier corresponds to the specified one.
     * @throws IllegalArgumentException                        in case when specified identifier is <code>null</code>
     *                                                         or empty.
     * @throws com.maxclay.exception.ResourceNotFoundException if there is no user with specified identifier.
     */
    User get(String id);

    /**
     * Registers new user to the system. Note, that object must contain valid values, in other case
     * {@link com.maxclay.exception.ValidationException} unchecked exception will be thrown.
     *
     * @param user not null, which contains information required for registration.
     * @return registered user.
     * @throws IllegalArgumentException                  in case when specified instance is <code>null</code>.
     * @throws com.maxclay.exception.ValidationException if provided instance contains invalid user data.
     */
    User register(User user);

    /**
     * Checks whether user with the specified identifier exists or not.
     *
     * @param id user's identifier.
     * @return 'true' if user with the specified identifier exists, 'false' otherwise.
     * @throws IllegalArgumentException in case when specified identifier is <code>null</code> or empty.
     */
    boolean exists(String id);

}
