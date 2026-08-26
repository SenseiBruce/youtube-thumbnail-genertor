package com.thumbnailgen.controller;

import com.thumbnailgen.dto.OverlayThresholdResponse;
import com.thumbnailgen.service.ThumbnailTextRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the brightness-variance cutoff used for title overlays.
 */
@RestController
@RequestMapping("/api")
public class OverlayThresholdController {

    @GetMapping("/overlay-threshold")
    public ResponseEntity<OverlayThresholdResponse> overlayThreshold() {
        return ResponseEntity.ok(
                new OverlayThresholdResponse(ThumbnailTextRenderer.OVERLAY_VARIANCE_THRESHOLD));
    }
}
