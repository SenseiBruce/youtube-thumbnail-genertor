package com.thumbnailgen.dto;

/**
 * Ending alpha of the dark gradient overlay drawn behind title text.
 */
public class OverlayAlphaResponse {

    private final int alpha;

    public OverlayAlphaResponse(int alpha) {
        this.alpha = alpha;
    }

    public int getAlpha() {
        return alpha;
    }
}
