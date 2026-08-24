package com.thumbnailgen.dto;

public class StylePreset {

    private final String id;
    private final String name;
    private final String primaryColor;
    private final String accentColor;
    private final String font;
    private final String placement;

    public StylePreset(
            String id,
            String name,
            String primaryColor,
            String accentColor,
            String font,
            String placement) {
        this.id = id;
        this.name = name;
        this.primaryColor = primaryColor;
        this.accentColor = accentColor;
        this.font = font;
        this.placement = placement;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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
