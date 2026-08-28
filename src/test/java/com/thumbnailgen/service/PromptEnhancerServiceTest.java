package com.thumbnailgen.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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

    @Test
    void enhance_longKeyword_isTruncatedToTwelveChars() {
        String result = service.enhance("supercalifragilistic");
        String[] parts = result.split("\\s+", 2);
        assertEquals(2, parts.length);
        assertTrue(HOOK_WORDS.contains(parts[0]));
        assertEquals(12, parts[1].length());
        assertEquals("SUPERCALIFRA", parts[1]);
    }

    @Test
    void enhanceVariants_returnsDistinctHookPlusKeyword() {
        List<String> variants = service.enhanceVariants("cooking pasta", 3);
        assertEquals(3, variants.size());
        assertEquals("INSANE COOKING", variants.get(0));
        assertEquals("CRAZY COOKING", variants.get(1));
        assertEquals("SHOCKING COOKING", variants.get(2));
        assertEquals(3, new HashSet<>(variants).size());
    }

    @Test
    void enhanceVariants_clampsCountAndHandlesBlank() {
        List<String> variants = service.enhanceVariants("   ", 9);
        assertEquals(5, variants.size());
        assertEquals("INSANE WATCH", variants.get(0));
        assertEquals("ULTIMATE WATCH", variants.get(4));
    }

    @Test
    void getContextWords_returnsEnhancerVocabulary() {
        List<String> words = service.getContextWords();
        assertEquals(10, words.size());
        assertTrue(words.contains("TRUTH"));
        assertTrue(words.contains("RESULT"));
    }

    @Test
    void getHookWords_returnsEnhancerVocabulary() {
        List<String> words = service.getHookWords();
        assertEquals(9, words.size());
        assertTrue(words.contains("INSANE"));
        assertTrue(words.contains("UNREAL"));
    }
}
