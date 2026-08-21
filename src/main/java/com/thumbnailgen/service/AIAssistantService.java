package com.thumbnailgen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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

@Service
public class AIAssistantService {

    private static final Logger log = LoggerFactory.getLogger(AIAssistantService.class);

    private final String apiKey;
    private final String chatUrl;
    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private final HuggingFaceService huggingFaceService;
    private final ThumbnailMetrics metrics;

    public AIAssistantService(
            @Value("${openai.api.key:}") String apiKey,
            @Value("${openai.api.url:https://api.openai.com/v1/chat/completions}") String chatUrl,
            ObjectMapper objectMapper,
            HuggingFaceService huggingFaceService,
            ThumbnailMetrics metrics) {
        this.apiKey = apiKey == null ? "" : apiKey;
        this.chatUrl = chatUrl;
        this.objectMapper = objectMapper;
        this.huggingFaceService = huggingFaceService;
        this.metrics = metrics;
        this.client = HttpClient.newHttpClient();
    }

    public ThumbnailStyle suggestThumbnailStyle(String topic) {
        return suggestThumbnailStyle(topic, null);
    }

    public ThumbnailStyle suggestThumbnailStyle(String topic, byte[] imageBytes) {
        log.info("AI Assistant called for topic: {}", topic);

        String placement = "center";
        if (imageBytes != null) {
            try {
                HuggingFaceService.PlacementResult hfResult = huggingFaceService.analyzeImageForPlacement(imageBytes);
                placement = hfResult.placement;
                log.info("HuggingFace suggests placement: {} (confidence: {})", placement, hfResult.confidence);
            } catch (AiIntegrationException e) {
                log.warn("HuggingFace placement failed, using default: {}", e.getMessage());
                metrics.recordFallback();
            }
        }

        if (apiKey.isBlank()) {
            log.warn("OpenAI API key not configured; using fallback style");
            metrics.recordFallback();
            return createDefaultWithPlacement(topic, placement);
        }

        try {
            String prompt = "Create YouTube thumbnail for: " + topic
                    + ". Return JSON: {\"title\":\"3-5 words\",\"primaryColor\":\"#hex\","
                    + "\"accentColor\":\"#hex\",\"font\":\"Impact\",\"placement\":\"" + placement
                    + "\",\"style\":\"bold|cinematic|fun|minimal\"}";

            String requestBody = buildRequestBody(prompt);
            log.debug("Sending request to OpenAI");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(chatUrl))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("OpenAI response status: {}", response.statusCode());

            if (response.statusCode() == 200) {
                ThumbnailStyle style = parseResponse(response.body(), topic);
                return new ThumbnailStyle(style.title, style.primaryColor, style.accentColor, style.font, placement);
            }

            log.error("OpenAI API error: status={}", response.statusCode());
            metrics.recordFallback();
            return createDefaultWithPlacement(topic, placement);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AiIntegrationException("OpenAI request interrupted", e);
        } catch (JsonProcessingException e) {
            throw new AiIntegrationException("Failed to build OpenAI request JSON", e);
        } catch (IOException e) {
            log.error("I/O error calling OpenAI: {}", e.getMessage());
            metrics.recordFallback();
            return createDefaultWithPlacement(topic, placement);
        }
    }

    private String buildRequestBody(String prompt) throws JsonProcessingException {
        ObjectNode root = objectMapper.createObjectNode();
        root.put("model", "gpt-3.5-turbo");
        root.put("max_tokens", 100);

        ArrayNode messages = root.putArray("messages");
        ObjectNode userMessage = messages.addObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);

        return objectMapper.writeValueAsString(root);
    }

    private ThumbnailStyle parseResponse(String response, String topic) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode contentNode = root.path("choices").path(0).path("message").path("content");
            if (contentNode.isMissingNode() || contentNode.isNull()) {
                metrics.recordFallback();
                return createDefault(topic);
            }

            String content = contentNode.asText().trim();
            if (content.startsWith("```")) {
                int start = content.indexOf('{');
                int end = content.lastIndexOf('}');
                if (start >= 0 && end > start) {
                    content = content.substring(start, end + 1);
                }
            }

            JsonNode styleJson = objectMapper.readTree(content);
            String title = textOrNull(styleJson, "title");
            String primary = textOrNull(styleJson, "primaryColor");
            String accent = textOrNull(styleJson, "accentColor");

            return new ThumbnailStyle(
                    title != null ? title : "EPIC " + topic.toUpperCase(),
                    primary != null ? primary : "#FFFFFF",
                    accent != null ? accent : "#FFFF00",
                    "Impact",
                    "center"
            );
        } catch (JsonProcessingException e) {
            log.warn("Failed to parse OpenAI response, using default", e);
            metrics.recordFallback();
            return createDefault(topic);
        }
    }

    private static String textOrNull(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || value.isNull()) {
            return null;
        }
        String text = value.asText();
        return text == null || text.isBlank() ? null : text;
    }

    private ThumbnailStyle createDefault(String topic) {
        return createDefaultWithPlacement(topic, "center");
    }

    private ThumbnailStyle createDefaultWithPlacement(String topic, String placement) {
        log.debug("Using fallback style for: {} with placement: {}", topic, placement);
        return new ThumbnailStyle("EPIC " + topic.toUpperCase(), "#FFFFFF", "#FFFF00", "Impact", placement);
    }

    public static class ThumbnailStyle {
        public final String title;
        public final String primaryColor;
        public final String accentColor;
        public final String font;
        public final String placement;

        public ThumbnailStyle(String title, String primaryColor, String accentColor, String font, String placement) {
            this.title = title;
            this.primaryColor = primaryColor;
            this.accentColor = accentColor;
            this.font = font;
            this.placement = placement;
        }
    }
}
