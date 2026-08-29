package com.thumbnailgen.controller;

import com.thumbnailgen.dto.MaxVariantCountResponse;
import com.thumbnailgen.service.PromptEnhancerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the maximum A/B title variant count.
 */
@RestController
@RequestMapping("/api")
public class MaxVariantCountController {

    @GetMapping("/max-variant-count")
    public ResponseEntity<MaxVariantCountResponse> maxVariantCount() {
        return ResponseEntity.ok(new MaxVariantCountResponse(PromptEnhancerService.MAX_VARIANT_COUNT));
    }
}
