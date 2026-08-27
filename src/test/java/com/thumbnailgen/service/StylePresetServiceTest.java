package com.thumbnailgen.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class StylePresetServiceTest {

    @Test
    void listPresets_returnsFourNamedPalettes() {
        StylePresetService service = new StylePresetService();
        assertEquals(4, service.listPresets().size());
        assertFalse(service.listPresets().get(0).getId().isBlank());
        assertEquals("center", service.listPresets().get(0).getPlacement());
    }
}
