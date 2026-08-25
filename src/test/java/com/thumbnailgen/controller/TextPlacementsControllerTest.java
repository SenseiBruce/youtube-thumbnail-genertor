package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TextPlacementsController.class)
class TextPlacementsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void textPlacements_matchesTextPlacementMath() throws Exception {
        mockMvc.perform(get("/api/text-placements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.placements.length()").value(5))
                .andExpect(jsonPath("$.placements[0].name").value("top"))
                .andExpect(jsonPath("$.placements[0].x").value(80))
                .andExpect(jsonPath("$.placements[0].y").value(60))
                .andExpect(jsonPath("$.placements[0].width").value(1120))
                .andExpect(jsonPath("$.placements[0].height").value(200))
                .andExpect(jsonPath("$.placements[4].name").value("center"))
                .andExpect(jsonPath("$.placements[4].x").value(360))
                .andExpect(jsonPath("$.placements[4].y").value(210))
                .andExpect(jsonPath("$.placements[4].width").value(560))
                .andExpect(jsonPath("$.placements[4].height").value(300));
    }
}
