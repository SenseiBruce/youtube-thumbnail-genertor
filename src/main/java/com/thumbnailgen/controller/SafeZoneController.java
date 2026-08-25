package com.thumbnailgen.controller;

import com.thumbnailgen.dto.SafeZoneResponse;
import com.thumbnailgen.service.ImageEnhancer;
import com.thumbnailgen.service.TextPlacement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.Rectangle;

/**
 * Exposes the thumbnail title safe zone for the 1280x720 canvas.
 */
@RestController
@RequestMapping("/api")
public class SafeZoneController {

    @GetMapping("/safe-zone")
    public ResponseEntity<SafeZoneResponse> safeZone() {
        Rectangle zone = TextPlacement.safeZone(ImageEnhancer.TARGET_WIDTH, ImageEnhancer.TARGET_HEIGHT);
        return ResponseEntity.ok(new SafeZoneResponse(zone.x, zone.y, zone.width, zone.height));
    }
}
