package com.thumbnailgen.controller;

import com.thumbnailgen.dto.MinFontSizeResponse;
import com.thumbnailgen.service.ThumbnailTextRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the shrink floor used when fitting title text into a region.
 */
@RestController
@RequestMapping("/api")
public class MinFontSizeController {

    @GetMapping("/min-font-size")
    public ResponseEntity<MinFontSizeResponse> minFontSize() {
        return ResponseEntity.ok(new MinFontSizeResponse(ThumbnailTextRenderer.MIN_FONT_SIZE));
    }
}
