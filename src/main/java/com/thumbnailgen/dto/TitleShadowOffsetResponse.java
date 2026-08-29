package com.thumbnailgen.dto;

/**
 * Drop-shadow pixel offset used when drawing the main title overlay.
 */
public class TitleShadowOffsetResponse {

    private final int titleShadowOffset;

    public TitleShadowOffsetResponse(int titleShadowOffset) {
        this.titleShadowOffset = titleShadowOffset;
    }

    public int getTitleShadowOffset() {
        return titleShadowOffset;
    }
}
