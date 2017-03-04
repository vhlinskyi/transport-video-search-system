package com.maxclay.service.impl;

import com.maxclay.dto.UserDto;
import com.maxclay.exception.ResourceNotFoundException;
import com.maxclay.exception.ValidationException;
import com.maxclay.model.User;
import com.maxclay.repository.UserRepository;
import com.maxclay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
@Service
public class UserServiceImpl implements UserService {

    private final Validator registrationValidator;
    private final ConversionService conversionService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(@Qualifier("registrationValidator") Validator registrationValidator,
                           @Qualifier("conversionService") ConversionService conversionService,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.registrationValidator = registrationValidator;
        this.conversionService = conversionService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ResourceNotFoundException("Authenticated user not found");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof org.springframework.security.core.userdetails.User)) {
            throw new ResourceNotFoundException("Authenticated user not found");
        }

        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User) principal;

        return getByEmail(userDetails.getUsername());
    }

    @Override
    public User get(String id) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("User's identifier can not be empty");
        }

        User user = userRepository.findOne(id);
        if (user == null) {
            String message = String.format("User with id = '%s' not found", id);
            throw new ResourceNotFoundException(message);
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getByEmail(String email) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("User's email can not be empty");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            String message = String.format("User with email = '%s' not found", email);
            throw new ResourceNotFoundException(message);
        }

        return user;
    }

    @Override
    public User register(UserDto userDto) {

        if (userDto == null) {
            throw new IllegalArgumentException("UserDto can not be null");
        }

        BindingResult errors = new BeanPropertyBindingResult(userDto, "userDto");
        registrationValidator.validate(userDto, errors);
        if (errors.hasErrors()) {
            throw new ValidationException("Validation errors occurred while registering user.", errors);
        }

        if (!emailAvailable(userDto.getEmail())) {
            throw new ValidationException("Validation errors occurred while registering user.",
                    "email is not available");
        }

        String plainPassword = userDto.getPassword();
        String encodedPassword = passwordEncoder.encode(plainPassword);

        User user = conversionService.convert(userDto, User.class);
        user.setId(null);
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    @Override
    public User save(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User can not be null");
        }

        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User can not be null");
        }

        userRepository.delete(user);
    }

    @Override
    public void delete(String id) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("User's identifier can not be empty");
        }

        userRepository.delete(id);
    }

    @Override
    public boolean exists(String id) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("User's identifier can not be empty");
        }

        return userRepository.exists(id);
    }

    @Override
    public boolean emailAvailable(String email) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("User's email can not be empty");
        }

        return userRepository.findByEmail(email) == null;
    }
}
