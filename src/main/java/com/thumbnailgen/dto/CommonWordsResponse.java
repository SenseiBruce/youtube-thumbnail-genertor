package com.thumbnailgen.dto;

import java.util.List;

/**
 * Stop-words skipped when picking a title keyword.
 */
public class CommonWordsResponse {

    private final List<String> commonWords;

    public CommonWordsResponse(List<String> commonWords) {
        this.commonWords = commonWords;
    }

    public List<String> getCommonWords() {
        return commonWords;
    }
}
