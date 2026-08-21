package com.thumbnailgen.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PromptEnhancerServiceTest {

    private static final Set<String> HOOK_WORDS = new HashSet<>(Arrays.asList(
            "INSANE", "CRAZY", "SHOCKING", "EPIC", "ULTIMATE", "SECRET", "EXPOSED", "MIND-BLOWN", "UNREAL"
    ));
    private static final Set<String> CONTEXT_WORDS = new HashSet<>(Arrays.asList(
            "TRUTH", "HACK", "TRICK", "METHOD", "REVEAL", "FACTS", "STORY", "MOMENT", "REACTION", "RESULT"
    ));

    private PromptEnhancerService service;

    @BeforeEach
    void setUp() {
        service = new PromptEnhancerService();
    }

    @Test
    void enhance_blankInput_returnsMustWatch() {
        assertEquals("MUST WATCH", service.enhance(null));
        assertEquals("MUST WATCH", service.enhance(""));
        assertEquals("MUST WATCH", service.enhance("   "));
    }

    @Test
    void enhance_withMeaningfulWord_returnsHookPlusKeyWord() {
        String result = service.enhance("cooking pasta");
        String[] parts = result.split("\\s+", 2);

        assertEquals(2, parts.length, "expected hook + keyword: " + result);
        assertTrue(HOOK_WORDS.contains(parts[0]), "unexpected hook: " + parts[0]);
        assertEquals("COOKING", parts[1]);
    }

    @Test
    void enhance_onlyCommonWords_returnsHookPlusContextWord() {
        String result = service.enhance("the and or");
        String[] parts = result.split("\\s+", 2);

        assertEquals(2, parts.length, "expected hook + context: " + result);
        assertTrue(HOOK_WORDS.contains(parts[0]), "unexpected hook: " + parts[0]);
        assertTrue(CONTEXT_WORDS.contains(parts[1]), "unexpected context: " + parts[1]);
    }
}
