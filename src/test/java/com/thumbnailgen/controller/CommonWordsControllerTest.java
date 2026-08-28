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

@WebMvcTest(CommonWordsController.class)
@Import(PromptEnhancerService.class)
class CommonWordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void commonWords_matchesEnhancerVocabulary() throws Exception {
        mockMvc.perform(get("/api/common-words"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commonWords", hasSize(18)))
                .andExpect(jsonPath("$.commonWords", hasItem("the")))
                .andExpect(jsonPath("$.commonWords", hasItem("were")));
    }
}
