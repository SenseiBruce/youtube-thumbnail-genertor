package com.thumbnailgen.dto;

/**
 * Outline stroke width used when drawing CTA overlay text.
 */
public class CtaStrokeWidthResponse {

    private final int ctaStrokeWidth;

    public CtaStrokeWidthResponse(int ctaStrokeWidth) {
        this.ctaStrokeWidth = ctaStrokeWidth;
    }

    public int getCtaStrokeWidth() {
        return ctaStrokeWidth;
    }
}
