package com.thumbnailgen.controller;

import com.thumbnailgen.dto.ThumbnailDefaults;
import com.thumbnailgen.service.ImageEnhancer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/defaults")
public class ThumbnailDefaultsController {

    @GetMapping
    public ThumbnailDefaults get() {
        return new ThumbnailDefaults(
                "1.2.0",
                ImageEnhancer.TARGET_WIDTH,
                ImageEnhancer.TARGET_HEIGHT,
                "png",
                MediaType.IMAGE_PNG_VALUE,
                60);
    }
}
