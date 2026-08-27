package com.thumbnailgen.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AllowedImageTypesResponseTest {

    @Test
    void getters_returnConstructorValues() {
        AllowedImageTypesResponse response =
                new AllowedImageTypesResponse(List.of("image/*"), "note");
        assertEquals(List.of("image/*"), response.getContentTypes());
        assertEquals("note", response.getNote());
    }
}
