package com.thumbnailgen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.thumbnailgen.metrics.ThumbnailMetrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AIAssistantServiceIntegrationTest {

    @RegisterExtension
    static final WireMockExtension OPEN_AI = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    private ThumbnailMetrics metrics;
    private AIAssistantService service;

    @BeforeEach
    void setUp() {
        metrics = new ThumbnailMetrics(new SimpleMeterRegistry());
        HuggingFaceService hf = new HuggingFaceService(
                "", OPEN_AI.baseUrl() + "/hf", new ObjectMapper(), metrics);
        service = new AIAssistantService(
                "test-openai-key",
                OPEN_AI.baseUrl() + "/v1/chat/completions",
                new ObjectMapper(),
                hf,
                metrics);
    }

    @Test
    void suggestThumbnailStyle_parsesSuccessfulOpenAiResponse() {
        String contentJson = "{\"title\":\"EPIC COOK\",\"primaryColor\":\"#111111\",\"accentColor\":\"#222222\"}";
        String body = "{\"choices\":[{\"message\":{\"content\":" + quote(contentJson) + "}}]}";

        OPEN_AI.stubFor(post(urlEqualTo("/v1/chat/completions"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(body)));

        AIAssistantService.ThumbnailStyle style = service.suggestThumbnailStyle("cooking");

        assertEquals("EPIC COOK", style.title);
        assertEquals("#111111", style.primaryColor);
        assertEquals("#222222", style.accentColor);
        OPEN_AI.verify(postRequestedFor(urlEqualTo("/v1/chat/completions"))
                .withHeader("Authorization", equalTo("Bearer test-openai-key")));
    }

    @Test
    void suggestThumbnailStyle_onErrorStatus_fallsBackAndIncrementsMetric() {
        OPEN_AI.stubFor(post(urlEqualTo("/v1/chat/completions"))
                .willReturn(aResponse().withStatus(500).withBody("boom")));

        double before = metrics.fallbackCount();
        AIAssistantService.ThumbnailStyle style = service.suggestThumbnailStyle("gaming");

        assertTrue(style.title.contains("GAMING"));
        assertEquals(before + 1.0, metrics.fallbackCount());
    }

    private static String quote(String value) {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
}
