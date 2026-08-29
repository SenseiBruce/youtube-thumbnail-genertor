package com.thumbnailgen.controller;

import com.thumbnailgen.dto.FallbackTitleResponse;
import com.thumbnailgen.service.PromptEnhancerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the fallback title used when a source title is blank.
 */
@RestController
@RequestMapping("/api")
public class FallbackTitleController {

    @GetMapping("/fallback-title")
    public ResponseEntity<FallbackTitleResponse> fallbackTitle() {
        return ResponseEntity.ok(new FallbackTitleResponse(PromptEnhancerService.FALLBACK_TITLE));
    }
}
