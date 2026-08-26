package com.thumbnailgen.controller;

import com.thumbnailgen.dto.DefaultPlacementResponse;
import com.thumbnailgen.service.TextPlacement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the default thumbnail text placement used when none is specified.
 */
@RestController
@RequestMapping("/api")
public class DefaultPlacementController {

    @GetMapping("/default-placement")
    public ResponseEntity<DefaultPlacementResponse> defaultPlacement() {
        return ResponseEntity.ok(new DefaultPlacementResponse(TextPlacement.DEFAULT_PLACEMENT));
    }
}
