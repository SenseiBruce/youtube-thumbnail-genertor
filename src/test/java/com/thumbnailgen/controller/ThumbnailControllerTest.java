package com.thumbnailgen.controller;

import com.thumbnailgen.service.AIAssistantService;
import com.thumbnailgen.service.ImageService;
import com.thumbnailgen.service.PromptEnhancerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ThumbnailController.class)
class ThumbnailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private PromptEnhancerService promptEnhancerService;

    @MockBean
    private AIAssistantService aiAssistantService;

    @Test
    void generate_returnsPng() throws Exception {
        byte[] pngBytes = new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47};
        when(imageService.generateThumbnail(any(byte[].class), eq("My Title")))
                .thenReturn(pngBytes);

        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[]{1, 2, 3});

        mockMvc.perform(multipart("/api/thumbnail/generate")
                        .file(file)
                        .param("title", "My Title"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(pngBytes));
    }

    @Test
    void generate_withEnhancePrompt_usesEnhancedTitle() throws Exception {
        when(promptEnhancerService.enhance("raw")).thenReturn("EPIC RAW");
        when(imageService.generateThumbnail(any(byte[].class), eq("EPIC RAW")))
                .thenReturn(new byte[]{9});

        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[]{1});

        mockMvc.perform(multipart("/api/thumbnail/generate")
                        .file(file)
                        .param("title", "raw")
                        .param("enhancePrompt", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG));
    }

    @Test
    void aiStyle_returnsJsonFields() throws Exception {
        when(aiAssistantService.suggestThumbnailStyle(anyString()))
                .thenReturn(new AIAssistantService.ThumbnailStyle(
                        "EPIC TOPIC", "#FFFFFF", "#FFFF00", "Impact", "center"));

        mockMvc.perform(post("/api/thumbnail/ai-style").param("topic", "gaming"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("EPIC TOPIC"))
                .andExpect(jsonPath("$.primaryColor").value("#FFFFFF"))
                .andExpect(jsonPath("$.placement").value("center"));
    }

    @Test
    void aiGenerate_returnsPngFromStyledPipeline() throws Exception {
        byte[] pngBytes = new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A};
        AIAssistantService.ThumbnailStyle style = new AIAssistantService.ThumbnailStyle(
                "AI TITLE", "#FFFFFF", "#FFFF00", "Impact", "top");
        when(aiAssistantService.suggestThumbnailStyle(eq("cooking"), any(byte[].class)))
                .thenReturn(style);
        when(imageService.generateAIThumbnail(any(byte[].class), eq(style)))
                .thenReturn(pngBytes);

        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[]{9, 9, 9});

        mockMvc.perform(multipart("/api/thumbnail/ai-generate")
                        .file(file)
                        .param("topic", "cooking"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(pngBytes));
    }

    @Test
    void generate_missingFile_returnsBadRequest() throws Exception {
        mockMvc.perform(multipart("/api/thumbnail/generate").param("title", "x"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void generate_rejectsNonImageContentType() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "notes.txt", MediaType.TEXT_PLAIN_VALUE, new byte[]{1, 2, 3});

        mockMvc.perform(multipart("/api/thumbnail/generate")
                        .file(file)
                        .param("title", "My Title"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("validation_error"));
    }
}
