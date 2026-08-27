package com.thumbnailgen.dto;

import java.util.List;

/**
 * All named title placements for the 1280x720 canvas.
 */
public class TextPlacementsResponse {

    private final List<PlacementRectResponse> placements;

    public TextPlacementsResponse(List<PlacementRectResponse> placements) {
        this.placements = placements;
    }

    public List<PlacementRectResponse> getPlacements() {
        return placements;
    }
}
