package com.thumbnailgen.dto;

import java.util.List;

/**
 * Hook vocabulary used by PromptEnhancerService.
 */
public class HookWordsResponse {

    private final List<String> hookWords;

    public HookWordsResponse(List<String> hookWords) {
        this.hookWords = hookWords;
    }

    public List<String> getHookWords() {
        return hookWords;
    }
}
