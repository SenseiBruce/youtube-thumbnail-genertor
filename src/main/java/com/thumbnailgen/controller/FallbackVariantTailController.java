package com.thumbnailgen.controller;

import com.thumbnailgen.dto.FallbackVariantTailResponse;
import com.thumbnailgen.service.PromptEnhancerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the fallback variant tail used when a source title is blank.
 */
@RestController
@RequestMapping("/api")
public class FallbackVariantTailController {

    @GetMapping("/fallback-variant-tail")
    public ResponseEntity<FallbackVariantTailResponse> fallbackVariantTail() {
        return ResponseEntity.ok(new FallbackVariantTailResponse(PromptEnhancerService.FALLBACK_VARIANT_TAIL));
    }
}
