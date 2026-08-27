package com.thumbnailgen.dto;

import java.util.List;

/**
 * Advisory image dimension check for YouTube thumbnails.
 */
public class ImageValidationResponse {

    private final int width;
    private final int height;
    private final double aspectRatio;
    private final boolean ok;
    private final List<String> warnings;

    public ImageValidationResponse(
            int width,
            int height,
            double aspectRatio,
            boolean ok,
            List<String> warnings) {
        this.width = width;
        this.height = height;
        this.aspectRatio = aspectRatio;
        this.ok = ok;
        this.warnings = warnings;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public boolean isOk() {
        return ok;
    }

    public List<String> getWarnings() {
        return warnings;
    }
}
