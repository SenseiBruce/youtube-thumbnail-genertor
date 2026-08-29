package com.thumbnailgen.service;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class PromptEnhancerService {
    public static final String FALLBACK_TITLE = "MUST WATCH";
    public static final String FALLBACK_VARIANT_TAIL = "WATCH";
    public static final int MIN_VARIANT_COUNT = 2;
    public static final int MAX_VARIANT_COUNT = 5;
    private static final List<String> HOOK_WORDS = Arrays.asList(
        "INSANE", "CRAZY", "SHOCKING", "EPIC", "ULTIMATE", "SECRET", "EXPOSED", "MIND-BLOWN", "UNREAL"
    );
    private static final List<String> CONTEXT_WORDS = Arrays.asList(
        "TRUTH", "HACK", "TRICK", "METHOD", "REVEAL", "FACTS", "STORY", "MOMENT", "REACTION", "RESULT"
    );
    private static final List<String> COMMON_WORDS = Arrays.asList(
        "the", "and", "or", "but", "in", "on", "at", "to", "for", "of", "with", "by", "a", "an", "is", "are", "was", "were"
    );
    private final Random random = new Random();

    public String enhance(String rawTitle) {
        if (rawTitle == null || rawTitle.isBlank()) {
            return FALLBACK_TITLE;
        }
        
        String[] words = rawTitle.trim().split("\\s+");
        String keyWord = findKeyWord(words);
        String hookWord = HOOK_WORDS.get(random.nextInt(HOOK_WORDS.size()));
        
        if (keyWord != null && keyWord.length() > 2) {
            String normalized = keyWord.toUpperCase();
            if (normalized.length() > 12) {
                normalized = normalized.substring(0, 12);
            }
            return hookWord + " " + normalized;
        } else {
            String contextWord = CONTEXT_WORDS.get(random.nextInt(CONTEXT_WORDS.size()));
            return hookWord + " " + contextWord;
        }
    }

    /**
     * Deterministic set of distinct title hooks for A/B thumbnail variants.
     * Count is clamped to 2–5.
     */
    public List<String> enhanceVariants(String rawTitle, int count) {
        int n = Math.min(MAX_VARIANT_COUNT, Math.max(MIN_VARIANT_COUNT, count));
        String tail;
        if (rawTitle == null || rawTitle.isBlank()) {
            tail = FALLBACK_VARIANT_TAIL;
        } else {
            String keyWord = findKeyWord(rawTitle.trim().split("\\s+"));
            if (keyWord != null && keyWord.length() > 2) {
                tail = keyWord.toUpperCase();
                if (tail.length() > 12) {
                    tail = tail.substring(0, 12);
                }
            } else {
                tail = CONTEXT_WORDS.get(0);
            }
        }
        ArrayList<String> variants = new ArrayList<>();
        for (int i = 0; i < n && i < HOOK_WORDS.size(); i++) {
            variants.add(HOOK_WORDS.get(i) + " " + tail);
        }
        return variants;
    }
    
    private String findKeyWord(String[] words) {
        String bestWord = null;
        int maxLength = 0;
        
        for (String word : words) {
            String clean = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (clean.length() > maxLength && !COMMON_WORDS.contains(clean)) {
                bestWord = clean;
                maxLength = clean.length();
            }
        }
        
        return bestWord;
    }

    public List<String> getHookWords() {
        return List.copyOf(HOOK_WORDS);
    }

    public List<String> getContextWords() {
        return List.copyOf(CONTEXT_WORDS);
    }

    public List<String> getCommonWords() {
        return List.copyOf(COMMON_WORDS);
    }
}
