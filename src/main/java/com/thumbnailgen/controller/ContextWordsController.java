package com.thumbnailgen.controller;

import com.thumbnailgen.dto.ContextWordsResponse;
import com.thumbnailgen.service.PromptEnhancerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the context words used when a title has no distinctive keyword.
 */
@RestController
@RequestMapping("/api")
public class ContextWordsController {

    private final PromptEnhancerService promptEnhancerService;

    public ContextWordsController(PromptEnhancerService promptEnhancerService) {
        this.promptEnhancerService = promptEnhancerService;
    }

    @GetMapping("/context-words")
    public ResponseEntity<ContextWordsResponse> contextWords() {
        return ResponseEntity.ok(new ContextWordsResponse(promptEnhancerService.getContextWords()));
    }
}
