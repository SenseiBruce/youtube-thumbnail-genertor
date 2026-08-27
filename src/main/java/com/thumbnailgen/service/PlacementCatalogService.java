package com.thumbnailgen.service;

import com.thumbnailgen.dto.PlacementCatalogEntry;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Catalog of text overlay placements (keys consumed by {@link TextPlacement}).
 */
@Service
public class PlacementCatalogService {

    private static final List<PlacementCatalogEntry> PLACEMENTS = List.of(
            new PlacementCatalogEntry("top", "Title band across the top third"),
            new PlacementCatalogEntry("bottom", "Title band across the bottom third"),
            new PlacementCatalogEntry("left", "Vertical band on the left half"),
            new PlacementCatalogEntry("right", "Vertical band on the right half"),
            new PlacementCatalogEntry("center", "Centered block (default / unknown keys)")
    );

    public List<PlacementCatalogEntry> listPlacements() {
        return PLACEMENTS;
    }
}
