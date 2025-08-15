
import org.example.indexer.api.util.WordTokenizer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class WordTokenizerTest {

  @Test
  void splitsOnNonLetters_andKeepsLetters() {
    String line = "Make, metrics; real-time analytics. email: support@company.com";
    var tokens = WordTokenizer.tokenize(line).toList();

    // Hyphen splits into two tokens
    assertTrue(tokens.containsAll(List.of("Make","metrics","real","time","analytics","email","support","company","com")));
    // No punctuation
    assertFalse(tokens.contains(","));
    assertFalse(tokens.contains("-"));
  }
}
