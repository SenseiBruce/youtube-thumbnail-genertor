package com.thumbnailgen.controller;

import com.thumbnailgen.dto.DefaultCtaResponse;
import com.thumbnailgen.service.ThumbnailTextRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the default CTA overlay string used on thumbnails.
 */
@RestController
@RequestMapping("/api")
public class DefaultCtaController {

    @GetMapping("/default-cta")
    public ResponseEntity<DefaultCtaResponse> defaultCta() {
        return ResponseEntity.ok(new DefaultCtaResponse(ThumbnailTextRenderer.DEFAULT_CTA));
    }
}
