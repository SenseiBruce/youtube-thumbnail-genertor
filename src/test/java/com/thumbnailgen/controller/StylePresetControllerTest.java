package com.thumbnailgen.controller;

import com.thumbnailgen.service.StylePresetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StylePresetController.class)
@Import(StylePresetService.class)
class StylePresetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void list_returnsNamedPresets() throws Exception {
        mockMvc.perform(get("/api/style-presets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id").value("bold-yellow"))
                .andExpect(jsonPath("$[0].primaryColor").value("#FFFF00"))
                .andExpect(jsonPath("$[2].name").value("Minimal White"));
    }
}
