package com.thumbnailgen.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleIOException_returnsBadRequestJson() {
        ResponseEntity<Map<String, Object>> response =
                handler.handleIOException(new IOException("Invalid image file"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("invalid_image", response.getBody().get("error"));
        assertTrue(response.getBody().get("message").toString().contains("Invalid image"));
    }

    @Test
    void handleIllegalArgument_returnsValidationError() {
        ResponseEntity<Map<String, Object>> response =
                handler.handleIllegalArgument(new IllegalArgumentException("bad title"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("validation_error", response.getBody().get("error"));
    }
}
