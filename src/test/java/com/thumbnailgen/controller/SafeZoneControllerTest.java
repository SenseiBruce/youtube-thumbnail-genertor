package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SafeZoneController.class)
class SafeZoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void safeZone_matchesTextPlacementOnYouTubeCanvas() throws Exception {
        mockMvc.perform(get("/api/safe-zone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.x").value(80))
                .andExpect(jsonPath("$.y").value(60))
                .andExpect(jsonPath("$.width").value(1120))
                .andExpect(jsonPath("$.height").value(600));
    }
}
