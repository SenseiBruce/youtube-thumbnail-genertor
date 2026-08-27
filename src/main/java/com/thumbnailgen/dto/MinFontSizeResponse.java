package com.thumbnailgen.dto;

/**
 * Minimum font size allowed while shrinking title text to fit a region.
 */
public class MinFontSizeResponse {

    private final int minSize;

    public MinFontSizeResponse(int minSize) {
        this.minSize = minSize;
    }

    public int getMinSize() {
        return minSize;
    }
}
