package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CtaFontController.class)
class CtaFontControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void ctaFont_isArial() throws Exception {
        mockMvc.perform(get("/api/cta-font"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.font").value("Arial"));
    }
}
