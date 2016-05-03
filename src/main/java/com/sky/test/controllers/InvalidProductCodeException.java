package com.sky.test.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidProductCodeException extends Exception {

    public InvalidProductCodeException() {
        super();
    }
}
