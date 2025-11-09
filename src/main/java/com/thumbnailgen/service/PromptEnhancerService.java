package com.thumbnailgen.service;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class PromptEnhancerService {
    private static final List<String> HOOK_WORDS = Arrays.asList(
        "INSANE", "CRAZY", "SHOCKING", "EPIC", "ULTIMATE", "SECRET", "EXPOSED", "MIND-BLOWN", "UNREAL"
    );
    private static final List<String> CONTEXT_WORDS = Arrays.asList(
        "TRUTH", "HACK", "TRICK", "METHOD", "REVEAL", "FACTS", "STORY", "MOMENT", "REACTION", "RESULT"
    );
    private final Random random = new Random();

    public String enhance(String rawTitle) {
        if (rawTitle == null || rawTitle.isBlank()) return "MUST WATCH";
        
        String[] words = rawTitle.trim().split("\\s+");
        String keyWord = findKeyWord(words);
        String hookWord = HOOK_WORDS.get(random.nextInt(HOOK_WORDS.size()));
        
        if (keyWord != null && keyWord.length() > 2) {
            return hookWord + " " + keyWord.toUpperCase();
        } else {
            String contextWord = CONTEXT_WORDS.get(random.nextInt(CONTEXT_WORDS.size()));
            return hookWord + " " + contextWord;
        }
    }
    
    private String findKeyWord(String[] words) {
        String[] commonWords = {"the", "and", "or", "but", "in", "on", "at", "to", "for", "of", "with", "by", "a", "an", "is", "are", "was", "were"};
        List<String> common = Arrays.asList(commonWords);
        
        String bestWord = null;
        int maxLength = 0;
        
        for (String word : words) {
            String clean = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (clean.length() > maxLength && !common.contains(clean)) {
                bestWord = clean;
                maxLength = clean.length();
            }
        }
        
        return bestWord;
    }
}