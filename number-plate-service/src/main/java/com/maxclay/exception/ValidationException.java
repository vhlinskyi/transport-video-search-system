package com.maxclay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Indicates that errors occurred while validating certain entity.
 *
 * @author Vlad Glinskiy
 */
@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    private List<String> errors;

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, BindingResult bindingResult) {
        this(message);
        if (bindingResult == null) {
            throw new IllegalArgumentException("Binding result can not be null");
        }
        setErrors(bindingResult.getAllErrors().stream().map(err -> err.getCode()).collect(Collectors.toList()));
    }

    public ValidationException(String message, List<String> errors) {
        this(message);
        setErrors(errors);
    }

    public ValidationException(String message, String... errors) {
        this(message);
        setErrors(Arrays.asList(errors));
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    /**
     * @return list of errors which provides more precise information about entity's corrupted fields and
     * validation errors. If there is no errors set, immutable empty list will be returned.
     */
    public List<String> getErrors() {
        return (errors != null) ? errors : Collections.emptyList();
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        message = (message == null) ? "" : message + " ";
        return String.format("%sErrors list: %s", message, getErrors());
    }
}
