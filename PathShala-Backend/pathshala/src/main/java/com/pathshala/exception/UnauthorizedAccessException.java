package com.pathshala.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
public class UnauthorizedAccessException extends BaseRuntimeException{
    public UnauthorizedAccessException(String error, String errorDescription) {
        super(error, errorDescription);
    }
}
