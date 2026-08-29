package com.thumbnailgen.dto;

/**
 * Minimum number of A/B title variants produced by PromptEnhancerService.
 */
public class MinVariantCountResponse {

    private final int minVariantCount;

    public MinVariantCountResponse(int minVariantCount) {
        this.minVariantCount = minVariantCount;
    }

    public int getMinVariantCount() {
        return minVariantCount;
    }
}
