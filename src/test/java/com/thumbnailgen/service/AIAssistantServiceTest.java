package com.thumbnailgen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thumbnailgen.metrics.ThumbnailMetrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AIAssistantServiceTest {

    private ThumbnailMetrics metrics;
    private AIAssistantService service;

    @BeforeEach
    void setUp() {
        metrics = new ThumbnailMetrics(new SimpleMeterRegistry());
        HuggingFaceService hf = new HuggingFaceService("", "http://localhost/hf", new ObjectMapper(), metrics);
        service = new AIAssistantService("", "http://localhost/openai", new ObjectMapper(), hf, metrics);
    }

    @Test
    void suggestThumbnailStyle_withoutApiKey_returnsFallback() {
        AIAssistantService.ThumbnailStyle style = service.suggestThumbnailStyle("gaming");

        assertTrue(style.title.contains("GAMING"));
        assertEquals("#FFFFFF", style.primaryColor);
        assertEquals("center", style.placement);
        assertEquals("Impact", style.font);
    }

    @Test
    void suggestThumbnailStyle_withoutApiKey_incrementsFallbackCounter() {
        double before = metrics.fallbackCount();
        service.suggestThumbnailStyle("gaming");
        assertEquals(before + 1.0, metrics.fallbackCount());
    }
}
