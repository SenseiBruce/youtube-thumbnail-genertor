package com.thumbnailgen.service;

import com.thumbnailgen.dto.FontCatalogEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FontCatalogService {

    private static final List<FontCatalogEntry> FONTS = List.of(
            new FontCatalogEntry("impact", "Impact", "Default main title overlay"),
            new FontCatalogEntry("arial", "Arial", "Default CTA overlay"),
            new FontCatalogEntry("montserrat", "Montserrat", "Cinematic style presets"),
            new FontCatalogEntry("inter", "Inter", "Minimal style presets")
    );

    public List<FontCatalogEntry> listFonts() {
        return FONTS;
    }
}
