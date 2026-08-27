package com.thumbnailgen.dto;

/**
 * Hard advisory title cap used by TitleValidator.
 */
public class MaxTitleLengthResponse {

    private final int maxLength;

    public MaxTitleLengthResponse(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }
}
