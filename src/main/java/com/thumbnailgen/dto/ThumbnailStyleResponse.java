package com.thumbnailgen.dto;

/**
 * Structured response for AI thumbnail style suggestions.
 */
public class ThumbnailStyleResponse {

    private final String title;
    private final String primaryColor;
    private final String accentColor;
    private final String font;
    private final String placement;

    public ThumbnailStyleResponse(
            String title,
            String primaryColor,
            String accentColor,
            String font,
            String placement) {
        this.title = title;
        this.primaryColor = primaryColor;
        this.accentColor = accentColor;
        this.font = font;
        this.placement = placement;
    }

    public String getTitle() {
        return title;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public String getAccentColor() {
        return accentColor;
    }

    public String getFont() {
        return font;
    }

    public String getPlacement() {
        return placement;
    }
}
