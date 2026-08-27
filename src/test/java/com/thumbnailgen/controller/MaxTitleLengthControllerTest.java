package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MaxTitleLengthController.class)
class MaxTitleLengthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void maxTitleLength_matchesValidatorConstant() throws Exception {
        mockMvc.perform(get("/api/max-title-length"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxLength").value(100));
    }
}
