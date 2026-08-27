package com.thumbnailgen.controller;

import com.thumbnailgen.dto.MaxTitleLengthResponse;
import com.thumbnailgen.service.TitleValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the hard 100-character title cap used by title validation.
 */
@RestController
@RequestMapping("/api")
public class MaxTitleLengthController {

    @GetMapping("/max-title-length")
    public ResponseEntity<MaxTitleLengthResponse> maxTitleLength() {
        return ResponseEntity.ok(new MaxTitleLengthResponse(TitleValidator.MAX_LENGTH));
    }
}
