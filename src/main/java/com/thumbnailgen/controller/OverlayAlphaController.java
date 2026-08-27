package com.thumbnailgen.controller;

import com.thumbnailgen.dto.OverlayAlphaResponse;
import com.thumbnailgen.service.ThumbnailTextRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the ending alpha of the title-area gradient overlay.
 */
@RestController
@RequestMapping("/api")
public class OverlayAlphaController {

    @GetMapping("/overlay-alpha")
    public ResponseEntity<OverlayAlphaResponse> overlayAlpha() {
        return ResponseEntity.ok(new OverlayAlphaResponse(ThumbnailTextRenderer.OVERLAY_END_ALPHA));
    }
}
