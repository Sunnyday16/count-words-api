package org.example.indexer.api.service;

import java.util.stream.Stream;

public interface Rule<R> {
  String name();
  R apply(Stream<String> words);
}
