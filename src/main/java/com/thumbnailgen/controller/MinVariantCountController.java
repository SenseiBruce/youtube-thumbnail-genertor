package com.thumbnailgen.controller;

import com.thumbnailgen.dto.MinVariantCountResponse;
import com.thumbnailgen.service.PromptEnhancerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the minimum A/B title variant count.
 */
@RestController
@RequestMapping("/api")
public class MinVariantCountController {

    @GetMapping("/min-variant-count")
    public ResponseEntity<MinVariantCountResponse> minVariantCount() {
        return ResponseEntity.ok(new MinVariantCountResponse(PromptEnhancerService.MIN_VARIANT_COUNT));
    }
}
