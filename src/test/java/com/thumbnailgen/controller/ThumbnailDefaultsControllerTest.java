package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ThumbnailDefaultsController.class)
class ThumbnailDefaultsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void get_returnsCanvasAndTitleDefaults() throws Exception {
        mockMvc.perform(get("/api/defaults"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("1.2.0"))
                .andExpect(jsonPath("$.canvasWidth").value(1280))
                .andExpect(jsonPath("$.canvasHeight").value(720))
                .andExpect(jsonPath("$.outputFormat").value("png"))
                .andExpect(jsonPath("$.mediaType").value("image/png"))
                .andExpect(jsonPath("$.recommendedTitleMaxChars").value(60));
    }
}
