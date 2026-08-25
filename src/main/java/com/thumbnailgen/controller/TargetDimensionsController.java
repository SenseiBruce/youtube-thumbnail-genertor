package com.thumbnailgen.controller;

import com.thumbnailgen.dto.TargetDimensionsResponse;
import com.thumbnailgen.service.ImageEnhancer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the 1280x720 thumbnail canvas used for generation.
 */
@RestController
@RequestMapping("/api")
public class TargetDimensionsController {

    @GetMapping("/target-dimensions")
    public ResponseEntity<TargetDimensionsResponse> targetDimensions() {
        return ResponseEntity.ok(
                new TargetDimensionsResponse(ImageEnhancer.TARGET_WIDTH, ImageEnhancer.TARGET_HEIGHT));
    }
}
