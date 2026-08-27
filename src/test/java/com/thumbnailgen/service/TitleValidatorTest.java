package com.thumbnailgen.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TitleValidatorTest {

    @Test
    void acceptsTitlesAtMaxLength() {
        String title = "x".repeat(TitleValidator.MAX_LENGTH);
        TitleValidator.TitleValidationResult result = TitleValidator.validate(title);
        assertTrue(result.isValid());
        assertEquals(100, result.getLength());
        assertEquals(100, result.getMaxLength());
        assertNull(result.getMessage());
    }

    @Test
    void rejectsTitlesOverMaxLength() {
        String title = "x".repeat(TitleValidator.MAX_LENGTH + 1);
        TitleValidator.TitleValidationResult result = TitleValidator.validate(title);
        assertFalse(result.isValid());
        assertEquals(101, result.getLength());
        assertEquals("Title exceeds 100 characters", result.getMessage());
    }

    @Test
    void rejectsBlankTitles() {
        TitleValidator.TitleValidationResult result = TitleValidator.validate("   ");
        assertFalse(result.isValid());
        assertEquals("Title must not be blank", result.getMessage());
    }
}
