package org.example.indexer.api.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.stream.Stream;

@Component
@ConfigurationProperties(prefix = "rules.count-starts-with")
public class CountStartsWithMRule implements Rule<Integer> {
  private String name = "countStartingWithM";
  private String letters = "m";

  @Override public String name() { return name; }

  @Override
  public Integer apply(Stream<String> words) {
    final String set = letters.toLowerCase(Locale.ROOT);
    return (int) words.filter(w -> !w.isEmpty()
        && set.indexOf(Character.toLowerCase(w.charAt(0))) >= 0)
      .count();
  }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getLetters() { return letters; }
  public void setLetters(String letters) { this.letters = letters; }
}
