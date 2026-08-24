package com.thumbnailgen.controller;

import com.thumbnailgen.service.ImageDimensionValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageValidationController.class)
class ImageValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageDimensionValidator imageDimensionValidator;

    @Test
    void validateImage_returnsCheck() throws Exception {
        when(imageDimensionValidator.inspect(any()))
                .thenReturn(new ImageDimensionValidator.ImageCheck(
                        1280, 720, 16.0 / 9.0, true, List.of()));

        MockMultipartFile file = new MockMultipartFile(
                "file", "thumb.png", MediaType.IMAGE_PNG_VALUE, new byte[]{1, 2, 3});

        mockMvc.perform(multipart("/api/thumbnail/validate-image").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.width").value(1280))
                .andExpect(jsonPath("$.height").value(720))
                .andExpect(jsonPath("$.ok").value(true));
    }
}
