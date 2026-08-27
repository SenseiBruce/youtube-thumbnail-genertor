package com.thumbnailgen.dto;

/**
 * Default font for the main thumbnail title overlay.
 */
public class TitleFontResponse {

    private final String font;

    public TitleFontResponse(String font) {
        this.font = font;
    }

    public String getFont() {
        return font;
    }
}
