package org.example.indexer.api.util;
import java.util.Arrays;
import java.util.stream.Stream;


public final class WordTokenizer {
    private WordTokenizer() {}
    // Split on any non-letter (handles punctuation). Keeps Unicode letters (\\p{L}).
    public static Stream<String> tokenize(String line) {
        return Arrays.stream(line.split("[^\\p{L}]+"))
                .filter(s -> !s.isBlank());
    }
}
