package com.thumbnailgen.controller;

import com.thumbnailgen.dto.HookWordsResponse;
import com.thumbnailgen.service.PromptEnhancerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the hook words used when enhancing thumbnail titles.
 */
@RestController
@RequestMapping("/api")
public class HookWordsController {

    private final PromptEnhancerService promptEnhancerService;

    public HookWordsController(PromptEnhancerService promptEnhancerService) {
        this.promptEnhancerService = promptEnhancerService;
    }

    @GetMapping("/hook-words")
    public ResponseEntity<HookWordsResponse> hookWords() {
        return ResponseEntity.ok(new HookWordsResponse(promptEnhancerService.getHookWords()));
    }
}
