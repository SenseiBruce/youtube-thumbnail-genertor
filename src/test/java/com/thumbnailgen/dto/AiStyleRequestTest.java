package com.thumbnailgen.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AiStyleRequestTest {

    @Test
    void topic_roundTrips() {
        AiStyleRequest request = new AiStyleRequest();
        request.setTopic("gaming");
        assertEquals("gaming", request.getTopic());
        assertEquals("cooking", new AiStyleRequest("cooking").getTopic());
    }
}
