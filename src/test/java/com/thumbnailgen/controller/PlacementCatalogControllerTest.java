package com.thumbnailgen.controller;

import com.thumbnailgen.service.PlacementCatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlacementCatalogController.class)
@Import(PlacementCatalogService.class)
class PlacementCatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void list_returnsKnownPlacementKeys() throws Exception {
        mockMvc.perform(get("/api/placements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id").value("top"))
                .andExpect(jsonPath("$[1].id").value("bottom"))
                .andExpect(jsonPath("$[2].id").value("left"))
                .andExpect(jsonPath("$[3].id").value("right"))
                .andExpect(jsonPath("$[4].id").value("center"));
    }
}
