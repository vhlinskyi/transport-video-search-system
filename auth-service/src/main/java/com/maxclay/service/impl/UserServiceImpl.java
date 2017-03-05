package com.maxclay.service.impl;

import com.maxclay.exception.ValidationException;
import com.maxclay.model.User;
import com.maxclay.repository.UserRepository;
import com.maxclay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

/**
 * @author Vlad Glinskiy
 */
@Service
public class UserServiceImpl implements UserService {

    private final Validator registrationValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(@Qualifier("registrationValidator") Validator registrationValidator,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.registrationValidator = registrationValidator;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User get(String id) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("User's identifier can not be empty");
        }

        return userRepository.findOne(id);
    }

    @Override
    public User register(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User can not be null");
        }

        BindingResult errors = new BeanPropertyBindingResult(user, "user");
        registrationValidator.validate(user, errors);
        if (errors.hasErrors()) {
            throw new ValidationException("Validation errors occurred while registering user.", errors);
        }

        if (exists(user.getUsername())) {
            throw new ValidationException("Validation errors occurred while registering user.",
                    "username is not available");
        }

        String plainPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(plainPassword);
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    @Override
    public boolean exists(String id) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("User's identifier can not be empty");
        }

        return userRepository.exists(id);
    }

}
