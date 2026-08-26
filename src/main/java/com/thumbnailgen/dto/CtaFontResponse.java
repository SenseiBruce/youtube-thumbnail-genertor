package com.thumbnailgen.dto;

/**
 * Font used for CTA overlay text under the main title.
 */
public class CtaFontResponse {

    private final String font;

    public CtaFontResponse(String font) {
        this.font = font;
    }

    public String getFont() {
        return font;
    }
}
