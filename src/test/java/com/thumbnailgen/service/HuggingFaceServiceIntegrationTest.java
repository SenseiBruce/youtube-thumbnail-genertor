package com.thumbnailgen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.thumbnailgen.metrics.ThumbnailMetrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HuggingFaceServiceIntegrationTest {

    @RegisterExtension
    static final WireMockExtension HF = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    private ThumbnailMetrics metrics;
    private HuggingFaceService service;

    @BeforeEach
    void setUp() {
        metrics = new ThumbnailMetrics(new SimpleMeterRegistry());
        service = new HuggingFaceService(
                "hf-test-token",
                HF.baseUrl() + "/models/facebook/detr-resnet-50",
                new ObjectMapper(),
                metrics);
    }

    @Test
    void analyzeImageForPlacement_personDetection_placesBottom() {
        HF.stubFor(post(urlEqualTo("/models/facebook/detr-resnet-50"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"label\":\"person\",\"score\":0.9}]")));

        HuggingFaceService.PlacementResult result = service.analyzeImageForPlacement(new byte[]{1, 2, 3});

        assertEquals("bottom", result.placement);
        assertEquals(0.8, result.confidence);
        HF.verify(postRequestedFor(urlEqualTo("/models/facebook/detr-resnet-50")));
    }

    @Test
    void analyzeImageForPlacement_foodDetection_placesTop() {
        HF.stubFor(post(urlEqualTo("/models/facebook/detr-resnet-50"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"label\":\"pizza\",\"score\":0.8}]")));

        HuggingFaceService.PlacementResult result = service.analyzeImageForPlacement(new byte[]{1});

        assertEquals("top", result.placement);
        assertEquals(0.7, result.confidence);
    }

    @Test
    void analyzeImageForPlacement_defaultDetection_placesCenter() {
        HF.stubFor(post(urlEqualTo("/models/facebook/detr-resnet-50"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"label\":\"car\",\"score\":0.7}]")));

        HuggingFaceService.PlacementResult result = service.analyzeImageForPlacement(new byte[]{1});

        assertEquals("center", result.placement);
        assertEquals(0.6, result.confidence);
    }

    @Test
    void analyzeImageForPlacement_errorStatus_fallsBackToCenter() {
        HF.stubFor(post(urlEqualTo("/models/facebook/detr-resnet-50"))
                .willReturn(aResponse().withStatus(503).withBody("unavailable")));

        double before = metrics.fallbackCount();
        HuggingFaceService.PlacementResult result = service.analyzeImageForPlacement(new byte[]{1});

        assertEquals("center", result.placement);
        assertEquals(0.5, result.confidence);
        assertEquals(before + 1.0, metrics.fallbackCount());
    }

    @Test
    void analyzeDetections_unitPaths() {
        assertEquals("bottom", service.analyzeDetections("face detected").placement);
        assertEquals("top", service.analyzeDetections("cake on table").placement);
        assertEquals("center", service.analyzeDetections("tree").placement);
    }
}
