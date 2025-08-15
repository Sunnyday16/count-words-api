package org.example.indexer.api.service;

import java.util.stream.Stream;

public interface Rule<R> {
  String name();                       // JSON key
  R apply(Stream<String> words);       // compute result from a fresh Stream
}
