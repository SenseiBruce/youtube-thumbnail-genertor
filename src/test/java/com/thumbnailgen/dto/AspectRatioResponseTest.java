package com.thumbnailgen.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AspectRatioResponseTest {

    @Test
    void getters_returnConstructorValues() {
        AspectRatioResponse response = new AspectRatioResponse("16:9", 1280, 720);
        assertEquals("16:9", response.getRatio());
        assertEquals(1280, response.getWidth());
        assertEquals(720, response.getHeight());
    }
}
