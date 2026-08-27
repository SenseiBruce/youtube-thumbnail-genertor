package com.thumbnailgen.dto;

/**
 * One overlay placement key supported by {@code TextPlacement}.
 */
public class PlacementCatalogEntry {

    private final String id;
    private final String description;

    public PlacementCatalogEntry(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
