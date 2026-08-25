package com.thumbnailgen.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AllowedImageTypesController.class)
class AllowedImageTypesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void allowedImageTypes_listsAcceptedContentTypes() throws Exception {
        mockMvc.perform(get("/api/allowed-image-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contentTypes[0]").value("image/*"))
                .andExpect(jsonPath("$.contentTypes[1]").value("application/octet-stream"))
                .andExpect(jsonPath("$.note").isNotEmpty());
    }
}
