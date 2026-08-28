package com.thumbnailgen.controller;

import com.thumbnailgen.dto.AspectToleranceResponse;
import com.thumbnailgen.service.ImageDimensionValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes how far an upload may stray from 16:9 before a warning is raised.
 */
@RestController
@RequestMapping("/api")
public class AspectToleranceController {

    @GetMapping("/aspect-tolerance")
    public ResponseEntity<AspectToleranceResponse> aspectTolerance() {
        return ResponseEntity.ok(
                new AspectToleranceResponse(ImageDimensionValidator.ASPECT_TOLERANCE));
    }
}
