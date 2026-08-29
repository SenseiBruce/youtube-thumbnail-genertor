package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TitleShadowOffsetController.class)
class TitleShadowOffsetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void titleShadowOffset_isSix() throws Exception {
        mockMvc.perform(get("/api/title-shadow-offset"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titleShadowOffset").value(6));
    }
}
