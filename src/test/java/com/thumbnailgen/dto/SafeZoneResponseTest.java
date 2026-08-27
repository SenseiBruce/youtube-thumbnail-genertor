package com.thumbnailgen.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SafeZoneResponseTest {

    @Test
    void getters_returnConstructorValues() {
        SafeZoneResponse response = new SafeZoneResponse(80, 60, 1120, 600);
        assertEquals(80, response.getX());
        assertEquals(60, response.getY());
        assertEquals(1120, response.getWidth());
        assertEquals(600, response.getHeight());
    }
}
