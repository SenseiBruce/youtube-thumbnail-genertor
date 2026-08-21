package com.thumbnailgen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AIAssistantServiceTest {

    @Test
    void suggestThumbnailStyle_withoutApiKey_returnsFallback() {
        HuggingFaceService hf = new HuggingFaceService("", new ObjectMapper());
        AIAssistantService service = new AIAssistantService("", new ObjectMapper(), hf);

        AIAssistantService.ThumbnailStyle style = service.suggestThumbnailStyle("gaming");

        assertTrue(style.title.contains("GAMING"));
        assertEquals("#FFFFFF", style.primaryColor);
        assertEquals("center", style.placement);
        assertEquals("Impact", style.font);
    }
}
