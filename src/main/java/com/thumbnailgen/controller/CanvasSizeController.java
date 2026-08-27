package com.thumbnailgen.controller;

import com.thumbnailgen.dto.CanvasSize;
import com.thumbnailgen.service.ImageEnhancer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/canvas")
public class CanvasSizeController {

    @GetMapping
    public CanvasSize get() {
        return new CanvasSize(ImageEnhancer.TARGET_WIDTH, ImageEnhancer.TARGET_HEIGHT);
    }
}
