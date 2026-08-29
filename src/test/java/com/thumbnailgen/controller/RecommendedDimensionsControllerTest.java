package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendedDimensionsController.class)
class RecommendedDimensionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void recommendedDimensions_matchImageDimensionValidator() throws Exception {
        mockMvc.perform(get("/api/recommended-dimensions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.width").value(1280))
                .andExpect(jsonPath("$.height").value(720));
    }
}
