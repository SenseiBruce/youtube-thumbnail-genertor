package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MaxVariantCountController.class)
class MaxVariantCountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void maxVariantCount_isFive() throws Exception {
        mockMvc.perform(get("/api/max-variant-count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxVariantCount").value(5));
    }
}
