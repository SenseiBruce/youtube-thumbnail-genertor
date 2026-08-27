package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DefaultPlacementController.class)
class DefaultPlacementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void defaultPlacement_isCenter() throws Exception {
        mockMvc.perform(get("/api/default-placement"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.placement").value("center"));
    }
}
