package com.thumbnailgen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service
public class HuggingFaceService {

    private static final Logger log = LoggerFactory.getLogger(HuggingFaceService.class);
    private static final String DETR_URL = "https://api-inference.huggingface.co/models/facebook/detr-resnet-50";

    private final String apiKey;
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public HuggingFaceService(
            @Value("${huggingface.api.key:}") String apiKey,
            ObjectMapper objectMapper) {
        this.apiKey = apiKey == null ? "" : apiKey;
        this.objectMapper = objectMapper;
        this.client = HttpClient.newHttpClient();
    }

    public PlacementResult analyzeImageForPlacement(byte[] imageBytes) {
        log.info("HuggingFace analyzing image for placement");

        if (apiKey.isBlank()) {
            log.warn("HuggingFace API key not configured; defaulting to center placement");
            return new PlacementResult("center", 0.5);
        }

        try {
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            ObjectNode body = objectMapper.createObjectNode();
            body.put("inputs", base64Image);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(DETR_URL))
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
        } catch (Exception e) {
            log.error("HuggingFace error: {}", e.getMessage(), e);
        }

        return new PlacementResult("center", 0.5);
    }

    private PlacementResult analyzeDetections(String response) {
        try {
            if (response.contains("person") || response.contains("face")) {
                log.info("Face/person detected — placing text at bottom");
                return new PlacementResult("bottom", 0.8);
            }

            if (response.contains("food") || response.contains("cake") || response.contains("pizza")) {
                log.info("Food detected — placing text at top");
                return new PlacementResult("top", 0.7);
            }

            return new PlacementResult("center", 0.6);
        } catch (Exception e) {
            log.warn("Failed to analyze detections", e);
            return new PlacementResult("center", 0.5);
        }
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
