package com.pathshala.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GenericExceptions extends BaseRuntimeException {
    public GenericExceptions(String errorCode, String errorDescription) {
        super(errorCode, errorDescription);
    }
}