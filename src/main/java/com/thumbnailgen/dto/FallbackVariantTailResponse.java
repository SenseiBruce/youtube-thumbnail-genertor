package com.thumbnailgen.dto;

/**
 * Deterministic tail used when building A/B title variants for a blank source.
 */
public class FallbackVariantTailResponse {

    private final String tail;

    public FallbackVariantTailResponse(String tail) {
        this.tail = tail;
    }

    public String getTail() {
        return tail;
    }
}
