package com.thumbnailgen.dto;

/**
 * YouTube thumbnail canvas size advertised to API clients.
 */
public class AspectRatioResponse {

    private final String ratio;
    private final int width;
    private final int height;

    public AspectRatioResponse(String ratio, int width, int height) {
        this.ratio = ratio;
        this.width = width;
        this.height = height;
    }

    public String getRatio() {
        return ratio;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
