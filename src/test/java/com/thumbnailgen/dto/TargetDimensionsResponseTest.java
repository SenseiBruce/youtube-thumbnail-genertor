package com.thumbnailgen.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TargetDimensionsResponseTest {

    @Test
    void gettersExposeConstructorValues() {
        TargetDimensionsResponse response = new TargetDimensionsResponse(1280, 720);
        assertEquals(1280, response.getWidth());
        assertEquals(720, response.getHeight());
    }
}
