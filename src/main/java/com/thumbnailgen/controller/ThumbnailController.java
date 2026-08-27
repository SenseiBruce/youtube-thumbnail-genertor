package com.thumbnailgen.controller;

import com.thumbnailgen.dto.EnhancePromptResponse;
import com.thumbnailgen.dto.ThumbnailStyleResponse;
import com.thumbnailgen.dto.TitleValidationResponse;
import com.thumbnailgen.service.AIAssistantService;
import com.thumbnailgen.service.ImageService;
import com.thumbnailgen.service.PromptEnhancerService;
import com.thumbnailgen.service.TitleValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    @PostMapping(value = "/validate-title")
    public ResponseEntity<TitleValidationResponse> validateTitle(
            @RequestParam("title") @NotBlank String title
    ) {
        TitleValidator.TitleValidationResult result = TitleValidator.validate(title);
        return ResponseEntity.ok(new TitleValidationResponse(
                result.getTitle(),
                result.getLength(),
                result.getMaxLength(),
                result.isValid(),
                result.getMessage()));
    @PostMapping(value = "/enhance-prompt")
    public ResponseEntity<EnhancePromptResponse> enhancePrompt(
            @RequestParam("title") @NotBlank String title
    ) {
        return ResponseEntity.ok(new EnhancePromptResponse(title, promptEnhancerService.enhance(title)));
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

    @PostMapping(value = "/generate-variants", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> generateVariants(
            @RequestParam("file") @NotNull MultipartFile file,
            @RequestParam("title") @NotBlank String title,
            @RequestParam(value = "count", required = false, defaultValue = "3") int count
    ) throws IOException {
        requireNonEmptyUpload(file);
        if (count < 2 || count > 5) {
            throw new IllegalArgumentException("count must be between 2 and 5");
        }
        List<String> titles = promptEnhancerService.enhanceVariants(title, count);
        byte[] imageBytes = file.getBytes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (int i = 0; i < titles.size(); i++) {
                byte[] png = imageService.generateThumbnail(imageBytes, titles.get(i));
                zos.putNextEntry(new ZipEntry("variant-" + (i + 1) + ".png"));
                zos.write(png);
                zos.closeEntry();
            }
            zos.putNextEntry(new ZipEntry("titles.txt"));
            zos.write(String.join("\n", titles).getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/zip"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"thumbnail-variants.zip\"")
                .body(baos.toByteArray());
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
