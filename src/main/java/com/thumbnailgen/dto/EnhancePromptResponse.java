package com.thumbnailgen.dto;

/**
 * Preview of a title after hook-word enhancement, without generating an image.
 */
public class EnhancePromptResponse {

    private final String original;
    private final String enhanced;

    public EnhancePromptResponse(String original, String enhanced) {
        this.original = original;
        this.enhanced = enhanced;
    }

    public String getOriginal() {
        return original;
    }

    public String getEnhanced() {
        return enhanced;
    }
}
