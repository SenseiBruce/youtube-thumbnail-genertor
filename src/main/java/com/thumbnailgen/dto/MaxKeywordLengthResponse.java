package com.thumbnailgen.dto;

/**
 * Max characters kept from a title keyword when building overlay variants.
 */
public class MaxKeywordLengthResponse {

    private final int maxKeywordLength;

    public MaxKeywordLengthResponse(int maxKeywordLength) {
        this.maxKeywordLength = maxKeywordLength;
    }

    public int getMaxKeywordLength() {
        return maxKeywordLength;
    }
}
