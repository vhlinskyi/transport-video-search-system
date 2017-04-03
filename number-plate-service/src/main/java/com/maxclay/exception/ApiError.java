package com.maxclay.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        this.setStatus(status);
        this.setMessage(message);
        this.setErrors(errors);
    }

    public ApiError(HttpStatus status, String message, String error) {
        this.setStatus(status);
        this.setMessage(message);
        this.setErrors(Arrays.asList(error));
    }

    public ApiError(HttpStatus status, String message) {
        this.setStatus(status);
        this.setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
