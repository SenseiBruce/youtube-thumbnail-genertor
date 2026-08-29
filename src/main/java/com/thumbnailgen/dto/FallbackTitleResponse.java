package com.thumbnailgen.dto;

/**
 * Fallback overlay title used when the source title is blank.
 */
public class FallbackTitleResponse {

    private final String title;

    public FallbackTitleResponse(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
