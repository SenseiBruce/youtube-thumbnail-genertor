package com.thumbnailgen.controller;

import com.thumbnailgen.service.FontCatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FontCatalogController.class)
@Import(FontCatalogService.class)
class FontCatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void list_returnsKnownOverlayFonts() throws Exception {
        mockMvc.perform(get("/api/fonts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].family").value("Impact"))
                .andExpect(jsonPath("$[1].family").value("Arial"));
    }
}
