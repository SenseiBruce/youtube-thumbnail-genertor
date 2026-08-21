package com.thumbnailgen.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThumbnailStyleResponseTest {

    @Test
    void getters_exposeConstructorValues() {
        ThumbnailStyleResponse response = new ThumbnailStyleResponse(
                "EPIC", "#fff", "#000", "Impact", "center");
        assertEquals("EPIC", response.getTitle());
        assertEquals("#fff", response.getPrimaryColor());
        assertEquals("#000", response.getAccentColor());
        assertEquals("Impact", response.getFont());
        assertEquals("center", response.getPlacement());
    }
}
