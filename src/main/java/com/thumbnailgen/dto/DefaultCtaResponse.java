package com.thumbnailgen.dto;

/**
 * Default CTA overlay text drawn on generated thumbnails.
 */
public class DefaultCtaResponse {

    private final String cta;

    public DefaultCtaResponse(String cta) {
        this.cta = cta;
    }

    public String getCta() {
        return cta;
    }
}
