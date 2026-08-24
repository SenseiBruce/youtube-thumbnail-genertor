package com.thumbnailgen.controller;

import com.thumbnailgen.dto.ImageValidationResponse;
import com.thumbnailgen.service.ImageDimensionValidator;
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
public class ImageValidationController {

    private final ImageDimensionValidator imageDimensionValidator;

    public ImageValidationController(ImageDimensionValidator imageDimensionValidator) {
        this.imageDimensionValidator = imageDimensionValidator;
    }

    @PostMapping(value = "/validate-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageValidationResponse> validateImage(
            @RequestParam("file") @NotNull MultipartFile file
    ) throws IOException {
        ImageDimensionValidator.ImageCheck check = imageDimensionValidator.inspect(file.getBytes());
        return ResponseEntity.ok(new ImageValidationResponse(
                check.getWidth(),
                check.getHeight(),
                check.getAspectRatio(),
                check.isOk(),
                check.getWarnings()));
    }
}
