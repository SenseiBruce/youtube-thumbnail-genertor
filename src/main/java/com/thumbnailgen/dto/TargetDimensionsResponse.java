package com.thumbnailgen.dto;

/**
 * YouTube thumbnail canvas size used by ImageEnhancer.
 */
public class TargetDimensionsResponse {

    private final int width;
    private final int height;

    public TargetDimensionsResponse(int width, int height) {
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
