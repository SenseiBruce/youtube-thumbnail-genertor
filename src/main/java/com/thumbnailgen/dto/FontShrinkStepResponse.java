package com.thumbnailgen.dto;

/**
 * Pixel step used while shrinking title text to fit a region.
 */
public class FontShrinkStepResponse {

    private final int step;

    public FontShrinkStepResponse(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }
}
