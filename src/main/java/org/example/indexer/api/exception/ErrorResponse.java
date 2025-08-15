package org.example.indexer.api.exception;

import java.time.Instant;

public record ErrorResponse(
    String error,
    String code,
    String message,
    String path,
    Instant timestamp
) {}
