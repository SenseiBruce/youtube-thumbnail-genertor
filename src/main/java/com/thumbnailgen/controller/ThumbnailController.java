package com.thumbnailgen.controller;

import com.thumbnailgen.dto.ThumbnailStyleResponse;
import com.thumbnailgen.service.AIAssistantService;
import com.thumbnailgen.service.ImageService;
import com.thumbnailgen.service.PromptEnhancerService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Validated
@RestController
@RequestMapping("/api/thumbnail")
public class ThumbnailController {

    private final ImageService imageService;
    private final PromptEnhancerService promptEnhancerService;
    private final AIAssistantService aiAssistantService;

    public ThumbnailController(
            ImageService imageService,
            PromptEnhancerService promptEnhancerService,
            AIAssistantService aiAssistantService) {
        this.imageService = imageService;
        this.promptEnhancerService = promptEnhancerService;
        this.aiAssistantService = aiAssistantService;
    }

    @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> generate(
            @RequestParam("file") @NotNull MultipartFile file,
            @RequestParam("title") @NotBlank String title,
            @RequestParam(value = "enhancePrompt", required = false, defaultValue = "false") boolean enhancePrompt
    ) throws IOException {
        requireNonEmptyUpload(file);
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
            @RequestParam("file") @NotNull MultipartFile file,
            @RequestParam("topic") @NotBlank String topic
    ) throws IOException {
        requireNonEmptyUpload(file);
        byte[] imageBytes = file.getBytes();
        AIAssistantService.ThumbnailStyle aiStyle = aiAssistantService.suggestThumbnailStyle(topic, imageBytes);
        byte[] result = imageService.generateAIThumbnail(imageBytes, aiStyle);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(result);
    }

    @PostMapping(value = "/ai-style")
    public ResponseEntity<ThumbnailStyleResponse> getAIStyle(
            @RequestParam("topic") @NotBlank String topic
    ) {
        AIAssistantService.ThumbnailStyle style = aiAssistantService.suggestThumbnailStyle(topic);
        return ResponseEntity.ok(new ThumbnailStyleResponse(
                style.title,
                style.primaryColor,
                style.accentColor,
                style.font,
                style.placement));
    }

    private static void requireNonEmptyUpload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file must not be empty");
        }
        String contentType = file.getContentType();
        if (contentType != null
                && !contentType.startsWith("image/")
                && !MediaType.APPLICATION_OCTET_STREAM_VALUE.equals(contentType)) {
            throw new IllegalArgumentException("Uploaded file must be an image");
        }
    }
}
