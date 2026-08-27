package com.thumbnailgen.dto;

import java.util.List;

/**
 * Content types accepted by thumbnail upload endpoints.
 */
public class AllowedImageTypesResponse {

    private final List<String> contentTypes;
    private final String note;

    public AllowedImageTypesResponse(List<String> contentTypes, String note) {
        this.contentTypes = contentTypes;
        this.note = note;
    }

    public List<String> getContentTypes() {
        return contentTypes;
    }

    public String getNote() {
        return note;
    }
}
