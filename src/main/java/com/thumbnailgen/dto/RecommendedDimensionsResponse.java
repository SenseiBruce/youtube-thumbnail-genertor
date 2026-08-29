package com.thumbnailgen.dto;

/**
 * Recommended upload size used by ImageDimensionValidator.
 */
public class RecommendedDimensionsResponse {

    private final int width;
    private final int height;

    public RecommendedDimensionsResponse(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
