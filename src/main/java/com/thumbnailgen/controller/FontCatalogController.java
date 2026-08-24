package com.thumbnailgen.controller;

import com.thumbnailgen.dto.FontCatalogEntry;
import com.thumbnailgen.service.FontCatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fonts")
public class FontCatalogController {

    private final FontCatalogService fontCatalogService;

    public FontCatalogController(FontCatalogService fontCatalogService) {
        this.fontCatalogService = fontCatalogService;
    }

    @GetMapping
    public List<FontCatalogEntry> list() {
        return fontCatalogService.listFonts();
    }
}
