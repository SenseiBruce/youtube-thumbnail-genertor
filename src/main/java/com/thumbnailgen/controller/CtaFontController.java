package com.thumbnailgen.controller;

import com.thumbnailgen.dto.CtaFontResponse;
import com.thumbnailgen.service.ThumbnailTextRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the Arial font used for thumbnail CTA overlays.
 */
@RestController
@RequestMapping("/api")
public class CtaFontController {

    @GetMapping("/cta-font")
    public ResponseEntity<CtaFontResponse> ctaFont() {
        return ResponseEntity.ok(new CtaFontResponse(ThumbnailTextRenderer.CTA_FONT));
    }
}
