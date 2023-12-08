package com.pathshala.exception;

import lombok.Getter;

@Getter
public class BaseRuntimeException extends RuntimeException {
    private final String errorCode;
    private final String errorDescription;

    public BaseRuntimeException(String errorCode, String errorDescription) {
        this(errorCode, errorDescription, errorDescription);
    }

    public BaseRuntimeException(String errorCode, String errorDescription, Exception e) {
        this(errorCode, errorDescription, errorDescription, e);
    }

    public BaseRuntimeException(String errorCode, String errorDescription, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public BaseRuntimeException(String errorCode, String errorDescription, String message, Exception e) {
        super(message, e);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

}
