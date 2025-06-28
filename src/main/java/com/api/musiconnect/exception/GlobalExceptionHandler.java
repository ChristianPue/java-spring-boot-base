package com.api.musiconnect.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(BusinessRuleException.class)
  public Map<String, Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
    Map<String, Object> errorResponse = new LinkedHashMap<>();
    errorResponse.put("timestamp", LocalDateTime.now());
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("details", "ResourceNotFoundException");
    return errorResponse;
  }

  @ExceptionHandler(DuplicatedResourceException.class)
  public Map<String, Object> handleDuplicatedResourceException(DuplicatedResourceException ex) {
    Map<String, Object> errorResponse = new LinkedHashMap<>();
    errorResponse.put("timestamp", LocalDateTime.now());
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("details", "DuplicatedResourceException");
    return errorResponse;
  }

  @ExceptionHandler(BusinessRuleException.class)
  public Map<String, Object> handleBusinessRuleException(BusinessRuleException ex) {
    Map<String, Object> errorResponse = new LinkedHashMap<>();
    errorResponse.put("timestamp", LocalDateTime.now());
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("details", "BusinessRuleException");
    return errorResponse;
  }
}
