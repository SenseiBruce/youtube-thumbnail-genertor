package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OverlayThresholdController.class)
class OverlayThresholdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void overlayThreshold_matchesRendererConstant() throws Exception {
        mockMvc.perform(get("/api/overlay-threshold"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.threshold").value(50));
    }
}
