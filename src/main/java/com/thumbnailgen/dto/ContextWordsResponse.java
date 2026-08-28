package com.thumbnailgen.dto;

import java.util.List;

/**
 * Context vocabulary used by PromptEnhancerService.
 */
public class ContextWordsResponse {

    private final List<String> contextWords;

    public ContextWordsResponse(List<String> contextWords) {
        this.contextWords = contextWords;
    }

    public List<String> getContextWords() {
        return contextWords;
    }
}
