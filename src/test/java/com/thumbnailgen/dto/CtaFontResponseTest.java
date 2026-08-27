package com.thumbnailgen.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CtaFontResponseTest {

    @Test
    void getFont_returnsConstructorValue() {
        CtaFontResponse response = new CtaFontResponse("Arial");
        assertEquals("Arial", response.getFont());
    }
}
