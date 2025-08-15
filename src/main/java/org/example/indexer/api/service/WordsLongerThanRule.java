package org.example.indexer.api.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@ConfigurationProperties(prefix = "rules.longer-than")
public class WordsLongerThanRule implements Rule<List<String>> {
  private String name = "wordsLongerThan5";
  private int min = 5;

  @Override public String name() { return name; }

  @Override
  public List<String> apply(Stream<String> words) {
    return words.filter(w -> w.length() > min)
                .collect(Collectors.toList());
  }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public int getMin() { return min; }
  public void setMin(int min) { this.min = min; }
}
