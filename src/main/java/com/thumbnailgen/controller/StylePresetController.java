package com.thumbnailgen.controller;

import com.thumbnailgen.dto.StylePreset;
import com.thumbnailgen.service.StylePresetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/style-presets")
public class StylePresetController {

    private final StylePresetService stylePresetService;

    public StylePresetController(StylePresetService stylePresetService) {
        this.stylePresetService = stylePresetService;
    }

    @GetMapping
    public List<StylePreset> list() {
        return stylePresetService.listPresets();
    }
}
