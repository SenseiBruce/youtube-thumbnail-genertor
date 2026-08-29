package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.mvc.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CtaShadowOffsetController.class)
class CtaShadowOffsetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void ctaShadowOffset_isFour() throws Exception {
        mockMvc.perform(get("/api/cta-shadow-offset"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ctaShadowOffset").value(4));
    }
}
