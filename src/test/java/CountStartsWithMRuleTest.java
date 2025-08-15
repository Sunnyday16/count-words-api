
import org.example.indexer.api.service.CountStartsWithMRule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountStartsWithMRuleTest {

  @Test
  void countsWordsStartingWithM_defaultLetters() {
    var words = List.of("Many","modern","systems","like","microservices","move","fast","Make","metrics","matter");
    var rule = new CountStartsWithMRule(); // defaults: name="countStartingWithM", letters="m"
    int count = rule.apply(words.stream());
    assertEquals(7, count);
  }

  @Test
  void countsWordsStartingWithAnyOfLetters_whenOverridden() {
    var words = List.of("Many","modern","microservices","programs","move","Make","apple");
    var rule = new CountStartsWithMRule();
    rule.setLetters("mp"); // m OR p
    int count = rule.apply(words.stream());
    // m*: Many, modern, microservices, move, Make  (5)
    // p*: programs                                 (1)
    assertEquals(6, count);
  }
}
