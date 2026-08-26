package com.thumbnailgen.controller;

import com.thumbnailgen.dto.TitleFontResponse;
import com.thumbnailgen.service.ThumbnailTextRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the Impact font used for main thumbnail titles.
 */
@RestController
@RequestMapping("/api")
public class TitleFontController {

    @GetMapping("/title-font")
    public ResponseEntity<TitleFontResponse> titleFont() {
        return ResponseEntity.ok(new TitleFontResponse(ThumbnailTextRenderer.TITLE_FONT));
    }
}
