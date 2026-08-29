package com.thumbnailgen.controller;

import com.thumbnailgen.dto.RecommendedDimensionsResponse;
import com.thumbnailgen.service.ImageDimensionValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the recommended 1280x720 size for uploaded thumbnail images.
 */
@RestController
@RequestMapping("/api")
public class RecommendedDimensionsController {

    @GetMapping("/recommended-dimensions")
    public ResponseEntity<RecommendedDimensionsResponse> recommendedDimensions() {
        return ResponseEntity.ok(
                new RecommendedDimensionsResponse(
                        ImageDimensionValidator.RECOMMENDED_WIDTH,
                        ImageDimensionValidator.RECOMMENDED_HEIGHT));
    }
}
