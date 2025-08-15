package org.example.indexer.api.service;

import org.example.indexer.api.exception.IndexingProcessingException;
import org.example.indexer.api.util.WordTokenizer;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

@Service
public class IndexService {
  private final List<Rule<?>> rules;

  public IndexService(List<Rule<?>> rules) {
    this.rules = rules;
  }

  public Map<String, Object> index(InputStream in) {
    List<String> words = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
      for (String line; (line = br.readLine()) != null; ) {
        WordTokenizer.tokenize(line).forEach(words::add);
      }
    } catch (IOException e) {
      throw IndexingProcessingException.internal(
              "INDEX_IO_ERROR", "Failed to process uploaded file", e);
    }

    Map<String, Object> out = new LinkedHashMap<>();
    for (Rule<?> r : rules) {
      try (Stream<String> s = words.stream()) {
        out.put(r.name(), r.apply(s));
      }
    }
    return out;
  }
}
