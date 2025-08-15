
import org.example.indexer.api.service.WordsLongerThanRule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordsLongerThanRuleTest {

  @Test
  void listsWordsLongerThanDefaultMin() {
    var words = List.of("Many","modern","systems","like","microservices","move","fast","Make","metrics","matter");
    var rule = new WordsLongerThanRule();
    var out = rule.apply(words.stream());

    assertTrue(out.contains("modern"));
    assertTrue(out.contains("systems"));
    assertTrue(out.contains("microservices"));
    assertTrue(out.contains("metrics"));
    assertTrue(out.contains("matter"));
    assertFalse(out.contains("Many"));
  }

  @Test
  void respectsCustomMin() {
    var words = List.of("microservices","monitoring","metrics","magnitude","apple","banana");
    var rule = new WordsLongerThanRule();
    rule.setMin(10);
    var out = rule.apply(words.stream());

    assertTrue(out.contains("microservices"));
    assertFalse(out.contains("monitoring"));
    assertFalse(out.contains("magnitude"));
  }
}
