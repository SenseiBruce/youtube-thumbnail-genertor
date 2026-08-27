package com.thumbnailgen.controller;

import com.thumbnailgen.service.PromptEnhancerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HookWordsController.class)
@Import(PromptEnhancerService.class)
class HookWordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void hookWords_matchesEnhancerVocabulary() throws Exception {
        mockMvc.perform(get("/api/hook-words"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hookWords", hasSize(9)))
                .andExpect(jsonPath("$.hookWords", hasItem("INSANE")))
                .andExpect(jsonPath("$.hookWords", hasItem("SECRET")));
    }
}
