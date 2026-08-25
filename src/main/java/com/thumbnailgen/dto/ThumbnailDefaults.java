package com.thumbnailgen.dto;

/**
 * Default thumbnail generation settings.
 */
public class ThumbnailDefaults {

    private final String version;
    private final int canvasWidth;
    private final int canvasHeight;
    private final String outputFormat;
    private final String mediaType;
    private final int recommendedTitleMaxChars;

    public ThumbnailDefaults(
            String version,
            int canvasWidth,
            int canvasHeight,
            String outputFormat,
            String mediaType,
            int recommendedTitleMaxChars) {
        this.version = version;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.outputFormat = outputFormat;
        this.mediaType = mediaType;
        this.recommendedTitleMaxChars = recommendedTitleMaxChars;
    }

    public String getVersion() {
        return version;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public String getMediaType() {
        return mediaType;
    }

    public int getRecommendedTitleMaxChars() {
        return recommendedTitleMaxChars;
    }
}
