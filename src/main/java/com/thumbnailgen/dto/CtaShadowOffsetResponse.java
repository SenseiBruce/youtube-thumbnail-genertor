package com.thumbnailgen.dto;

/**
 * Drop-shadow pixel offset used when drawing CTA overlay text.
 */
public class CtaShadowOffsetResponse {

    private final int ctaShadowOffset;

    public CtaShadowOffsetResponse(int ctaShadowOffset) {
        this.ctaShadowOffset = ctaShadowOffset;
    }

    public int getCtaShadowOffset() {
        return ctaShadowOffset;
    }
}
