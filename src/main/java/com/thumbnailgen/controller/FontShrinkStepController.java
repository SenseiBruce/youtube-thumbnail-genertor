package com.thumbnailgen.controller;

import com.thumbnailgen.dto.FontShrinkStepResponse;
import com.thumbnailgen.service.ThumbnailTextRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the step used when shrinking title text to fit a region.
 */
@RestController
@RequestMapping("/api")
public class FontShrinkStepController {

    @GetMapping("/font-shrink-step")
    public ResponseEntity<FontShrinkStepResponse> fontShrinkStep() {
        return ResponseEntity.ok(new FontShrinkStepResponse(ThumbnailTextRenderer.FONT_SHRINK_STEP));
    }
}
