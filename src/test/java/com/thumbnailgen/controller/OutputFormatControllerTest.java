package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OutputFormatController.class)
class OutputFormatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void get_returnsPngMediaType() throws Exception {
        mockMvc.perform(get("/api/output-format"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.format").value("png"))
                .andExpect(jsonPath("$.mediaType").value("image/png"));
    }
}
