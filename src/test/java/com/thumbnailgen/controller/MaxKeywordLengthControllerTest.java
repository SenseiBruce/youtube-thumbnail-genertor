package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MaxKeywordLengthController.class)
class MaxKeywordLengthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void maxKeywordLength_isTwelve() throws Exception {
        mockMvc.perform(get("/api/max-keyword-length"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxKeywordLength").value(12));
    }
}
