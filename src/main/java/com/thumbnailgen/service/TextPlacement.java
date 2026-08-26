package com.thumbnailgen.service;

import java.awt.Rectangle;

/**
 * Pure placement math for thumbnail text regions within a safe zone.
 */
public final class TextPlacement {

    /** Default overlay zone when a style omits placement. */
    public static final String DEFAULT_PLACEMENT = "center";

    private TextPlacement() {
    }

    public static Rectangle safeZone(int width, int height) {
        return new Rectangle(80, 60, width - 160, height - 120);
    }

    public static Rectangle getPlacementArea(Rectangle safeZone, String placement) {
        String key = placement == null ? DEFAULT_PLACEMENT : placement.toLowerCase();
        String key = placement == null ? "center" : placement.toLowerCase();
        switch (key) {
            case "top":
                return new Rectangle(safeZone.x, safeZone.y, safeZone.width, safeZone.height / 3);
            case "bottom":
                return new Rectangle(
                        safeZone.x,
                        safeZone.y + 2 * safeZone.height / 3,
                        safeZone.width,
                        safeZone.height / 3);
            case "left":
                return new Rectangle(safeZone.x, safeZone.y, safeZone.width / 2, safeZone.height);
            case "right":
                return new Rectangle(
                        safeZone.x + safeZone.width / 2,
                        safeZone.y,
                        safeZone.width / 2,
                        safeZone.height);
            default:
                return new Rectangle(
                        safeZone.x + safeZone.width / 4,
                        safeZone.y + safeZone.height / 4,
                        safeZone.width / 2,
                        safeZone.height / 2);
        }
    }
}
