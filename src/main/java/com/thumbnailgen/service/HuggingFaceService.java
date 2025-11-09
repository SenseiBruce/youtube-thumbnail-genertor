package com.thumbnailgen.service;

import org.springframework.stereotype.Service;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Base64;

@Service
public class HuggingFaceService {

    private final String apiKey = "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"; // Replace with your HF token
    private final HttpClient client = HttpClient.newHttpClient();

    public PlacementResult analyzeImageForPlacement(byte[] imageBytes) {
        System.out.println("🔍 HuggingFace analyzing image for placement...");
        
        try {
            // Use object detection model to find faces, objects
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-inference.huggingface.co/models/facebook/detr-resnet-50"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"inputs\":\"" + base64Image + "\"}"))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("📥 HF Response: " + response.body());
            
            if (response.statusCode() == 200) {
                return analyzeDetections(response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ HF Error: " + e.getMessage());
        }
        
        return new PlacementResult("center", 0.5);
    }

    private PlacementResult analyzeDetections(String response) {
        try {
            // Simple analysis - if face detected, place text at bottom
            if (response.contains("person") || response.contains("face")) {
                System.out.println("👤 Face/person detected - placing text at bottom");
                return new PlacementResult("bottom", 0.8);
            }
            
            // If food detected, place at top
            if (response.contains("food") || response.contains("cake") || response.contains("pizza")) {
                System.out.println("🍕 Food detected - placing text at top");
                return new PlacementResult("top", 0.7);
            }
            
            // Default center placement
            return new PlacementResult("center", 0.6);
            
        } catch (Exception e) {
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