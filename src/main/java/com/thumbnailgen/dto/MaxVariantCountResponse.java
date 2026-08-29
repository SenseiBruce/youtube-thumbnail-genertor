package com.thumbnailgen.dto;

/**
 * Maximum number of A/B title variants produced by PromptEnhancerService.
 */
public class MaxVariantCountResponse {

    private final int maxVariantCount;

    public MaxVariantCountResponse(int maxVariantCount) {
        this.maxVariantCount = maxVariantCount;
    }

    public int getMaxVariantCount() {
        return maxVariantCount;
    }
}
