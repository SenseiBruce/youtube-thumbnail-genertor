package com.thumbnailgen.dto;

/**
 * Advisory title-length check for thumbnail clients.
 */
public class TitleValidationResponse {

    private final String title;
    private final int length;
    private final int maxLength;
    private final boolean valid;
    private final String message;

    public TitleValidationResponse(
            String title,
            int length,
            int maxLength,
            boolean valid,
            String message) {
        this.title = title;
        this.length = length;
        this.maxLength = maxLength;
        this.valid = valid;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }
}
