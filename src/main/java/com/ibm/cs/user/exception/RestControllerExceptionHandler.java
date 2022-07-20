package com.ibm.cs.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<RegistrationError> resourceNotFoundException(MethodArgumentNotValidException methodArgumentNotValidException) {
        return methodArgumentNotValidException.getFieldErrors()
                .stream().map(fieldError -> RegistrationError.builder()
                        .field(fieldError.getField())
                        .errorMessage(fieldError.getDefaultMessage())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @ExceptionHandler(value = {UserNotEligibleException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<RegistrationError> resourceNotFoundException(UserNotEligibleException userNotEligibleException) {
        return Arrays.asList(RegistrationError.builder()
                .field("ipAddress")
                .errorMessage(userNotEligibleException.getMessage())
                .build());
    }
}
