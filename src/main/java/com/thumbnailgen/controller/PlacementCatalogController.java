package com.thumbnailgen.controller;

import com.thumbnailgen.dto.PlacementCatalogEntry;
import com.thumbnailgen.service.PlacementCatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/placements")
public class PlacementCatalogController {

    private final PlacementCatalogService placementCatalogService;

    public PlacementCatalogController(PlacementCatalogService placementCatalogService) {
        this.placementCatalogService = placementCatalogService;
    }

    @GetMapping
    public List<PlacementCatalogEntry> list() {
        return placementCatalogService.listPlacements();
    }
}
