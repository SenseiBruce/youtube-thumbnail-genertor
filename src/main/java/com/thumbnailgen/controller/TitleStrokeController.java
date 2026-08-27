package com.thumbnailgen.controller;

import com.thumbnailgen.dto.TitleStrokeResponse;
import com.thumbnailgen.service.ThumbnailTextRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes outline stroke widths for title and CTA overlay text.
 */
@RestController
@RequestMapping("/api")
public class TitleStrokeController {

    @GetMapping("/title-stroke")
    public ResponseEntity<TitleStrokeResponse> titleStroke() {
        return ResponseEntity.ok(
                new TitleStrokeResponse(
                        ThumbnailTextRenderer.TITLE_STROKE_WIDTH,
                        ThumbnailTextRenderer.CTA_STROKE_WIDTH));
    }
}
