package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MinFontSizeController.class)
class MinFontSizeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void minFontSize_matchesRendererConstant() throws Exception {
        mockMvc.perform(get("/api/min-font-size"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.minSize").value(30));
    }
}
