package com.pathshala.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Gets thrown if resource is not found
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseRuntimeException {
  public NotFoundException(String error, String errorDescription) {
    super(error, errorDescription);
  }
}
