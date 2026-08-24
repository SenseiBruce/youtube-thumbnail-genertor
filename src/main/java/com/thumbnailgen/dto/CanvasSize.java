package com.thumbnailgen.dto;

/**
 * Output canvas size used for generated thumbnails.
 */
public class CanvasSize {

    private final int width;
    private final int height;

    public CanvasSize(int width, int height) {
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
