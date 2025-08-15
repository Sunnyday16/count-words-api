package org.example.indexer.api.model;
import java.util.List;

public record Result(int countStartingWithM, List<String> wordsLongerThan5) {}
