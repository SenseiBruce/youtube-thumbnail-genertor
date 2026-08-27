package com.thumbnailgen.dto;

/**
 * Generated thumbnail output format.
 */
public class OutputFormat {

    private final String format;
    private final String mediaType;

    public OutputFormat(String format, String mediaType) {
        this.format = format;
        this.mediaType = mediaType;
    }

    public String getFormat() {
        return format;
    }

    public String getMediaType() {
        return mediaType;
    }
}
