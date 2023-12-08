package com.pathshala.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RecordExistsException extends BaseRuntimeException{
    public RecordExistsException(String errorCode, String errorDescription) {
        super(errorCode, errorDescription);
    }
}
