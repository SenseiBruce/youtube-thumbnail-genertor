package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TitleStrokeController.class)
class TitleStrokeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void titleStroke_matchesRendererConstants() throws Exception {
        mockMvc.perform(get("/api/title-stroke"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(8))
                .andExpect(jsonPath("$.cta").value(5));
    }
}
