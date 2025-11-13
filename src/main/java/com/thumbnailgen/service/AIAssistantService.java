package com.thumbnailgen.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.*;

@Service
public class AIAssistantService {

    private final String apiKey = "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";
    private final HttpClient client = HttpClient.newHttpClient();
    
    @Autowired
    private HuggingFaceService huggingFaceService;

    public ThumbnailStyle suggestThumbnailStyle(String topic) {
        return suggestThumbnailStyle(topic, null);
    }
    
    public ThumbnailStyle suggestThumbnailStyle(String topic, byte[] imageBytes) {
        System.out.println("🤖 AI Assistant called for topic: " + topic);
        
        // Get placement from HuggingFace if image provided
        String placement = "center";
        if (imageBytes != null) {
            try {
                HuggingFaceService.PlacementResult hfResult = huggingFaceService.analyzeImageForPlacement(imageBytes);
                placement = hfResult.placement;
                System.out.println("🎯 HuggingFace suggests placement: " + placement + " (confidence: " + hfResult.confidence + ")");
            } catch (Exception e) {
                System.out.println("⚠️ HuggingFace placement failed, using default");
            }
        }
        
        try {
          String prompt = "Create YouTube thumbnail for: " + topic + ". Return JSON: {\"title\":\"3-5 words\",\"primaryColor\":\"#hex\",\"accentColor\":\"#hex\",\"font\":\"Impact\",\"placement\":\"" + placement + "\",\"style\":\"bold|cinematic|fun|minimal\"}";
            
            String json = "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"" + prompt + "\"}],\"max_tokens\":100}";
            
            System.out.println("📤 Sending to OpenAI: " + json);
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("📥 OpenAI Response (" + response.statusCode() + "): " + response.body());
            
            if (response.statusCode() == 200) {
                ThumbnailStyle style = parseResponse(response.body(), topic);
                // Override placement with HuggingFace result
                return new ThumbnailStyle(style.title, style.primaryColor, style.accentColor, style.font, placement);
            } else {
                System.out.println("❌ OpenAI API Error: " + response.statusCode());
                return createDefaultWithPlacement(topic, placement);
            }
        } catch (Exception e) {
            System.out.println("❌ Exception calling OpenAI: " + e.getMessage());
            return createDefaultWithPlacement(topic, placement);
        }
    }

    private ThumbnailStyle parseResponse(String response, String topic) {
        try {
            String content = response.substring(response.indexOf("\"content\":\"") + 11);
            content = content.substring(0, content.indexOf("\""));
            
            String title = extract(content, "title");
            String primary = extract(content, "primaryColor");
            String accent = extract(content, "accentColor");
            
            return new ThumbnailStyle(
                title != null ? title : "EPIC " + topic.toUpperCase(),
                primary != null ? primary : "#FFFFFF",
                accent != null ? accent : "#FFFF00",
                "Impact",
                "center"
            );
        } catch (Exception e) {
            return createDefault(topic);
        }
    }

    private String extract(String json, String key) {
        try {
            String pattern = "\"" + key + "\":\"";
            int start = json.indexOf(pattern) + pattern.length();
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) {
            return null;
        }
    }

    private ThumbnailStyle createDefault(String topic) {
        return createDefaultWithPlacement(topic, "center");
    }
    
    private ThumbnailStyle createDefaultWithPlacement(String topic, String placement) {
        System.out.println("🔄 Using fallback style for: " + topic + " with placement: " + placement);
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