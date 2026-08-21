package com.thumbnailgen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumbnailgen.exception.AiIntegrationException;
import com.thumbnailgen.metrics.ThumbnailMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service
public class HuggingFaceService {

    private static final Logger log = LoggerFactory.getLogger(HuggingFaceService.class);

    private final String apiKey;
    private final String inferenceUrl;
    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private final ThumbnailMetrics metrics;

    public HuggingFaceService(
            @Value("${huggingface.api.key:}") String apiKey,
            @Value("${huggingface.api.url:https://api-inference.huggingface.co/models/facebook/detr-resnet-50}") String inferenceUrl,
            ObjectMapper objectMapper,
            ThumbnailMetrics metrics) {
        this.apiKey = apiKey == null ? "" : apiKey;
        this.inferenceUrl = inferenceUrl;
        this.objectMapper = objectMapper;
        this.metrics = metrics;
        this.client = HttpClient.newHttpClient();
    }

    public PlacementResult analyzeImageForPlacement(byte[] imageBytes) {
        log.info("HuggingFace analyzing image for placement");

        if (apiKey.isBlank()) {
            log.warn("HuggingFace API key not configured; defaulting to center placement");
            metrics.recordFallback();
            return new PlacementResult("center", 0.5);
        }

        try {
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            ObjectNode body = objectMapper.createObjectNode();
            body.put("inputs", base64Image);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(inferenceUrl))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("HuggingFace response status: {}", response.statusCode());

            if (response.statusCode() == 200) {
                return analyzeDetections(response.body());
            }
            log.warn("HuggingFace non-OK status: {}", response.statusCode());
            metrics.recordFallback();
            return new PlacementResult("center", 0.5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AiIntegrationException("HuggingFace request interrupted", e);
        } catch (JsonProcessingException e) {
            throw new AiIntegrationException("Failed to build HuggingFace request JSON", e);
        } catch (IOException e) {
            log.error("HuggingFace I/O error: {}", e.getMessage());
            metrics.recordFallback();
            return new PlacementResult("center", 0.5);
        }
    }

    PlacementResult analyzeDetections(String response) {
        if (response.contains("person") || response.contains("face")) {
            log.info("Face/person detected — placing text at bottom");
            return new PlacementResult("bottom", 0.8);
        }

        if (response.contains("food") || response.contains("cake") || response.contains("pizza")) {
            log.info("Food detected — placing text at top");
            return new PlacementResult("top", 0.7);
        }

        return new PlacementResult("center", 0.6);
    }

    public static class PlacementResult {
        public final String placement;
        public final double confidence;

        public PlacementResult(String placement, double confidence) {
            this.placement = placement;
            this.confidence = confidence;
        }
    }
}
