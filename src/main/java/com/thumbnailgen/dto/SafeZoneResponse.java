package com.thumbnailgen.dto;

/**
 * Text safe-zone rectangle used when laying out thumbnail titles.
 */
public class SafeZoneResponse {

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public SafeZoneResponse(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
