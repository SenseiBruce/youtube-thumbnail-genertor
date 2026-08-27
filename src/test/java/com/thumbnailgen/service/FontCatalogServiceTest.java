package com.thumbnailgen.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FontCatalogServiceTest {

    @Test
    void listFonts_includesImpactAndArial() {
        FontCatalogService service = new FontCatalogService();
        assertEquals(4, service.listFonts().size());
        assertEquals("impact", service.listFonts().get(0).getId());
        assertFalse(service.listFonts().get(1).getUsage().isBlank());
    }
}
