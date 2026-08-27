package com.thumbnailgen.dto;

/**
 * Default text overlay placement used when AI style omits a zone.
 */
public class DefaultPlacementResponse {

    private final String placement;

    public DefaultPlacementResponse(String placement) {
        this.placement = placement;
    }

    public String getPlacement() {
        return placement;
    }
}
