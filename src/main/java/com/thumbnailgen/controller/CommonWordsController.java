package com.thumbnailgen.controller;

import com.thumbnailgen.dto.CommonWordsResponse;
import com.thumbnailgen.service.PromptEnhancerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes stop-words the enhancer skips when choosing a title keyword.
 */
@RestController
@RequestMapping("/api")
public class CommonWordsController {

    private final PromptEnhancerService promptEnhancerService;

    public CommonWordsController(PromptEnhancerService promptEnhancerService) {
        this.promptEnhancerService = promptEnhancerService;
    }

    @GetMapping("/common-words")
    public ResponseEntity<CommonWordsResponse> commonWords() {
        return ResponseEntity.ok(new CommonWordsResponse(promptEnhancerService.getCommonWords()));
    }
}
