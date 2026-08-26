package com.thumbnailgen.dto;

/**
 * Outline stroke widths used when drawing title and CTA text.
 */
public class TitleStrokeResponse {

    private final int title;
    private final int cta;

    public TitleStrokeResponse(int title, int cta) {
        this.title = title;
        this.cta = cta;
    }

    public int getTitle() {
        return title;
    }

    public int getCta() {
        return cta;
    }
}
