package com.mohammadalsalkini.webservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mohammad Alsalkini
 * @project webservice
 * @created 18.04.2020 - 19:50
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message, null, false, false);
    }
}
