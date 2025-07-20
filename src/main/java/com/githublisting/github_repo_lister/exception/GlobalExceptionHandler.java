package com.githublisting.github_repo_lister.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UserNotFoundDTO handleUserNotFound(UserNotFoundException exception) {
        return new UserNotFoundDTO(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }
}
