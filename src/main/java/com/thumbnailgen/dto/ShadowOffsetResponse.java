package com.thumbnailgen.dto;

/**
 * Drop-shadow pixel offsets used when drawing title and CTA text.
 */
public class ShadowOffsetResponse {

    private final int title;
    private final int cta;

    public ShadowOffsetResponse(int title, int cta) {
        this.title = title;
        this.cta = cta;
    }

    public int getTitle() {
        return title;
    }

    public int getCta() {
        return cta;
    }
}
