package com.thumbnailgen.controller;

import com.thumbnailgen.dto.CtaShadowOffsetResponse;
import com.thumbnailgen.service.ThumbnailTextRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the CTA overlay drop-shadow offset.
 */
@RestController
@RequestMapping("/api")
public class CtaShadowOffsetController {

    @GetMapping("/cta-shadow-offset")
    public ResponseEntity<CtaShadowOffsetResponse> ctaShadowOffset() {
        return ResponseEntity.ok(
                new CtaShadowOffsetResponse(ThumbnailTextRenderer.CTA_SHADOW_OFFSET));
    }
}
