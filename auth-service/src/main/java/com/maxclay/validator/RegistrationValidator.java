package com.maxclay.validator;

import com.maxclay.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for checking user's registration data while registering new user.
 *
 * @author Vlad Glinskiy
 */
@Component("registrationValidator")
public class RegistrationValidator implements Validator {

    @Value("${password.minimalLength: 5}")
    private Integer minimalPasswordLength;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * Validates user's registration data while registering new user. Accepts instances of
     * {@link com.maxclay.model.User} class, in other case throw {@link IllegalArgumentException}.
     *
     * @param o      instance of {@link com.maxclay.model.User} class, which stores user's registration data
     * @param errors
     * @throws IllegalArgumentException if specified object is not instance of the {@link com.maxclay.model.User} class
     *                                  or if instance is <code>null</code>.
     */
    @Override
    public void validate(Object o, Errors errors) {

        if (o == null) {
            throw new IllegalArgumentException("UserDto can not be null");
        }

        if (!(o instanceof User)) {
            throw new IllegalArgumentException("Validated object must be instance of UserDto class");
        }

        User user = (User) o;

        String username = user.getUsername();
        if (username == null || username.isEmpty()) {
            errors.rejectValue("username", "username can not be empty");
        }

        String password = user.getPassword();
        if (password == null || password.isEmpty()) {
            errors.rejectValue("password", "password can not be empty");
        }

    }
}