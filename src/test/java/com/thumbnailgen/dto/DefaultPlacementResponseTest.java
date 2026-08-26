package com.thumbnailgen.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultPlacementResponseTest {

    @Test
    void getPlacement_returnsConstructorValue() {
        DefaultPlacementResponse response = new DefaultPlacementResponse("center");
        assertEquals("center", response.getPlacement());
    }
}
