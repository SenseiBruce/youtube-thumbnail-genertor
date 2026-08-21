package com.thumbnailgen.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request schema for AI style suggestion endpoint fields.
 */
public class AiStyleRequest {

    @NotBlank
    @Size(min = 1, max = 200)
    private String topic;

    public AiStyleRequest() {
    }

    public AiStyleRequest(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
