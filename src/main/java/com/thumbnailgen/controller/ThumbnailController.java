package com.thumbnailgen.controller;
import com.thumbnailgen.service.ImageService;
import com.thumbnailgen.service.PromptEnhancerService;
import com.thumbnailgen.service.AIAssistantService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/thumbnail")
public class ThumbnailController {

    private final ImageService imageService;
    private final PromptEnhancerService promptEnhancerService;
    private final AIAssistantService aiAssistantService;

    public ThumbnailController(ImageService imageService, PromptEnhancerService promptEnhancerService, AIAssistantService aiAssistantService) {
        this.imageService = imageService;
        this.promptEnhancerService = promptEnhancerService;
        this.aiAssistantService = aiAssistantService;
    }

    @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> generate(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam(value = "enhancePrompt", required = false, defaultValue = "false") boolean enhancePrompt
    ) throws IOException {

        if (enhancePrompt) {
            title = promptEnhancerService.enhance(title);
        }

        byte[] result = imageService.generateThumbnail(file.getBytes(), title);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(result);
    }

    @PostMapping(value = "/ai-generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> generateAIThumbnail(
            @RequestParam("file") MultipartFile file,
            @RequestParam("topic") String topic
    ) throws IOException {
        byte[] imageBytes = file.getBytes();
        AIAssistantService.ThumbnailStyle aiStyle = aiAssistantService.suggestThumbnailStyle(topic, imageBytes);
        byte[] result = imageService.generateAIThumbnail(imageBytes, aiStyle);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(result);
    }

    @PostMapping(value = "/ai-style")
    public ResponseEntity<Map<String, String>> getAIStyle(
            @RequestParam("topic") String topic
    ) {
        AIAssistantService.ThumbnailStyle style = aiAssistantService.suggestThumbnailStyle(topic);
        
        Map<String, String> response = new HashMap<>();
        response.put("title", style.title);
        response.put("primaryColor", style.primaryColor);
        response.put("accentColor", style.accentColor);
        response.put("font", style.font);
        response.put("placement", style.placement);
        
        return ResponseEntity.ok(response);
    }
}