package com.maxclay.validator;

import com.maxclay.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator for checking user's registration data while registering new user.
 *
 * @author Vlad Glinskiy
 */
@Component("registrationValidator")
public class RegistrationValidator implements Validator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;

    @Value("${password.minimalLength: 5}")
    private Integer minimalPasswordLength;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    /**
     * Validates user's registration data while registering new user. Accepts instances of
     * {@link com.maxclay.dto.UserDto} class, in other case throw {@link IllegalArgumentException}.
     *
     * @param o      instance of {@link com.maxclay.dto.UserDto} class, which stores user's registration data
     * @param errors
     */
    @Override
    public void validate(Object o, Errors errors) {

        if (o == null) {
            throw new IllegalArgumentException("UserDto can not be null");
        }

        if (!(o instanceof UserDto)) {
            throw new IllegalArgumentException("Validated object must be instance of UserDto class");
        }

        UserDto userDto = (UserDto) o;

        String firstName = userDto.getName();
        if (firstName == null || firstName.isEmpty()) {
            errors.rejectValue("name", "name can not be empty");
        }

        String email = userDto.getEmail();
        if (email == null || email.isEmpty()) {
            errors.rejectValue("email", "email can not be empty");
        } else {

            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
            boolean isValidEmail = matcher.matches();
            if (!isValidEmail) {
                errors.rejectValue("email", "email is not valid");
            }
        }

        String password = userDto.getPassword();
        if (password == null || password.isEmpty()) {
            errors.rejectValue("password", "password can not be empty");
        }

        String matchingPassword = userDto.getMatchingPassword();
        if (matchingPassword == null || matchingPassword.isEmpty()) {
            errors.rejectValue("matchingPassword", "matchingPassword can not be empty");
        } else if (!matchingPassword.equals(password)) {
            errors.rejectValue("matchingPassword", "passwords don't match");
        }

    }
}