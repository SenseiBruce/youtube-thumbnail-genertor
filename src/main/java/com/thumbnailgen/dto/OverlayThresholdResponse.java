package com.thumbnailgen.dto;

/**
 * Brightness-variance cutoff that triggers a title overlay.
 */
public class OverlayThresholdResponse {

    private final int threshold;

    public OverlayThresholdResponse(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }
}
