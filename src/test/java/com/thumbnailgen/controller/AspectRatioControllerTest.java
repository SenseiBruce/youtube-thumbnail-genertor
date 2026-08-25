package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AspectRatioController.class)
class AspectRatioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void aspectRatio_returnsYouTubeCanvas() throws Exception {
        mockMvc.perform(get("/api/aspect-ratio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratio").value("16:9"))
                .andExpect(jsonPath("$.width").value(1280))
                .andExpect(jsonPath("$.height").value(720));
    }

    @Test
    void simplifyRatio_reducesCommonFactors() {
        assertEquals("16:9", AspectRatioController.simplifyRatio(1280, 720));
        assertEquals("1:1", AspectRatioController.simplifyRatio(0, 0));
    }
}
