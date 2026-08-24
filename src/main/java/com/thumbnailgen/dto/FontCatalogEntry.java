package com.thumbnailgen.dto;

public class FontCatalogEntry {

    private final String id;
    private final String family;
    private final String usage;

    public FontCatalogEntry(String id, String family, String usage) {
        this.id = id;
        this.family = family;
        this.usage = usage;
    }

    public String getId() {
        return id;
    }

    public String getFamily() {
        return family;
    }

    public String getUsage() {
        return usage;
    }
}
