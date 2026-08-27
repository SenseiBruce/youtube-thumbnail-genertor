package com.thumbnailgen.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlacementRectResponseTest {

    @Test
    void gettersExposeConstructorValues() {
        PlacementRectResponse rect = new PlacementRectResponse("top", 80, 60, 1120, 200);
        assertEquals("top", rect.getName());
        assertEquals(80, rect.getX());
        assertEquals(60, rect.getY());
        assertEquals(1120, rect.getWidth());
        assertEquals(200, rect.getHeight());

        TextPlacementsResponse response = new TextPlacementsResponse(List.of(rect));
        assertEquals(1, response.getPlacements().size());
        assertEquals("top", response.getPlacements().get(0).getName());
    }
}
