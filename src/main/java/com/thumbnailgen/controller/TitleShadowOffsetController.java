package com.thumbnailgen.controller;

import com.thumbnailgen.dto.TitleShadowOffsetResponse;
import com.thumbnailgen.service.ThumbnailTextRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the main title overlay drop-shadow offset.
 */
@RestController
@RequestMapping("/api")
public class TitleShadowOffsetController {

    @GetMapping("/title-shadow-offset")
    public ResponseEntity<TitleShadowOffsetResponse> titleShadowOffset() {
        return ResponseEntity.ok(
                new TitleShadowOffsetResponse(ThumbnailTextRenderer.TITLE_SHADOW_OFFSET));
    }
}
