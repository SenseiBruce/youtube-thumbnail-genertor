package com.thumbnailgen.controller;

import com.thumbnailgen.dto.AllowedImageTypesResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Documents which upload content types the thumbnail API accepts.
 */
@RestController
@RequestMapping("/api")
public class AllowedImageTypesController {

    static final List<String> CONTENT_TYPES = List.of(
            "image/*",
            MediaType.APPLICATION_OCTET_STREAM_VALUE);

    @GetMapping("/allowed-image-types")
    public ResponseEntity<AllowedImageTypesResponse> allowedImageTypes() {
        return ResponseEntity.ok(new AllowedImageTypesResponse(
                CONTENT_TYPES,
                "Uploads must be non-empty images (image/*) or unlabeled binary (application/octet-stream)."));
    }
}
