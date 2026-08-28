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

@WebMvcTest(ContextWordsController.class)
@Import(PromptEnhancerService.class)
class ContextWordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextWords_matchesEnhancerVocabulary() throws Exception {
        mockMvc.perform(get("/api/context-words"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contextWords", hasSize(10)))
                .andExpect(jsonPath("$.contextWords", hasItem("TRUTH")))
                .andExpect(jsonPath("$.contextWords", hasItem("RESULT")));
    }
}
