package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MinVariantCountController.class)
class MinVariantCountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void minVariantCount_isTwo() throws Exception {
        mockMvc.perform(get("/api/min-variant-count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.minVariantCount").value(2));
    }
}
