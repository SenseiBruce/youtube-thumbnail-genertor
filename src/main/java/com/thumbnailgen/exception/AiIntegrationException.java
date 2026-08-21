package com.thumbnailgen.exception;

/**
 * Raised when an unexpected AI provider failure occurs (not a soft fallback).
 */
public class AiIntegrationException extends RuntimeException {

    public AiIntegrationException(String message) {
        super(message);
    }

    public AiIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
