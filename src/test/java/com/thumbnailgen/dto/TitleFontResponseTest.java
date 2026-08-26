package com.thumbnailgen.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TitleFontResponseTest {

    @Test
    void getFont_returnsConstructorValue() {
        TitleFontResponse response = new TitleFontResponse("Impact");
        assertEquals("Impact", response.getFont());
    }
}
