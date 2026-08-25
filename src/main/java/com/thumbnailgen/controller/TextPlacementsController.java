package com.thumbnailgen.controller;

import com.thumbnailgen.dto.PlacementRectResponse;
import com.thumbnailgen.dto.TextPlacementsResponse;
import com.thumbnailgen.service.ImageEnhancer;
import com.thumbnailgen.service.TextPlacement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Exposes named title placement rectangles for the YouTube thumbnail canvas.
 */
@RestController
@RequestMapping("/api")
public class TextPlacementsController {

    private static final String[] NAMES = {"top", "bottom", "left", "right", "center"};

    @GetMapping("/text-placements")
    public ResponseEntity<TextPlacementsResponse> textPlacements() {
        Rectangle safe = TextPlacement.safeZone(ImageEnhancer.TARGET_WIDTH, ImageEnhancer.TARGET_HEIGHT);
        List<PlacementRectResponse> placements = new ArrayList<>();
        for (String name : NAMES) {
            Rectangle rect = TextPlacement.getPlacementArea(safe, name);
            placements.add(new PlacementRectResponse(name, rect.x, rect.y, rect.width, rect.height));
        }
        return ResponseEntity.ok(new TextPlacementsResponse(placements));
    }
}
