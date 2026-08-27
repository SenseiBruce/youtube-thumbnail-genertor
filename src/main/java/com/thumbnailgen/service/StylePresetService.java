package com.thumbnailgen.service;

import com.thumbnailgen.dto.StylePreset;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StylePresetService {

    private static final List<StylePreset> PRESETS = List.of(
            new StylePreset("bold-yellow", "Bold Yellow", "#FFFF00", "#111111", "Impact", "center"),
            new StylePreset("cinematic-red", "Cinematic Red", "#FF2D2D", "#FFFFFF", "Montserrat", "bottom"),
            new StylePreset("minimal-white", "Minimal White", "#FFFFFF", "#111111", "Inter", "center"),
            new StylePreset("fun-cyan", "Fun Cyan", "#00E5FF", "#FF00AA", "Comic Sans MS", "top")
    );

    public List<StylePreset> listPresets() {
        return PRESETS;
    }
}
