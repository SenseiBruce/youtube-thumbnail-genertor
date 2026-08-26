package com.thumbnailgen.controller;

import com.thumbnailgen.dto.ShadowOffsetResponse;
import com.thumbnailgen.service.ThumbnailTextRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes drop-shadow offsets for title and CTA overlay text.
 */
@RestController
@RequestMapping("/api")
public class ShadowOffsetController {

    @GetMapping("/shadow-offset")
    public ResponseEntity<ShadowOffsetResponse> shadowOffset() {
        return ResponseEntity.ok(
                new ShadowOffsetResponse(
                        ThumbnailTextRenderer.TITLE_SHADOW_OFFSET,
                        ThumbnailTextRenderer.CTA_SHADOW_OFFSET));
    }
}
