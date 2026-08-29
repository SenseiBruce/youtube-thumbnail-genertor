package com.thumbnailgen.controller;

import com.thumbnailgen.dto.CtaStrokeWidthResponse;
import com.thumbnailgen.service.ThumbnailTextRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the CTA overlay outline stroke width.
 */
@RestController
@RequestMapping("/api")
public class CtaStrokeWidthController {

    @GetMapping("/cta-stroke-width")
    public ResponseEntity<CtaStrokeWidthResponse> ctaStrokeWidth() {
        return ResponseEntity.ok(new CtaStrokeWidthResponse(ThumbnailTextRenderer.CTA_STROKE_WIDTH));
    }
}
