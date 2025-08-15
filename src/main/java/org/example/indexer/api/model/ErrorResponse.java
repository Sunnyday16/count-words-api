package org.example.indexer.api.model;

import java.time.Instant;

public record ErrorResponse(
    String error,
    String code,
    String message,
    String path,
    Instant timestamp
) {}
