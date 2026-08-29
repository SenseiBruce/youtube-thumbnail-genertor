package com.thumbnailgen.controller;

import com.thumbnailgen.dto.MaxKeywordLengthResponse;
import com.thumbnailgen.service.PromptEnhancerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the keyword truncation length used for overlay title variants.
 */
@RestController
@RequestMapping("/api")
public class MaxKeywordLengthController {

    @GetMapping("/max-keyword-length")
    public ResponseEntity<MaxKeywordLengthResponse> maxKeywordLength() {
        return ResponseEntity.ok(
                new MaxKeywordLengthResponse(PromptEnhancerService.MAX_KEYWORD_LENGTH));
    }
}
