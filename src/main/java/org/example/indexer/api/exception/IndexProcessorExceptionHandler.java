package org.example.indexer.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class IndexProcessorExceptionHandler {

  @ExceptionHandler(IndexingProcessingException.class)
  public ResponseEntity<ErrorResponse> handleIndexing(IndexingProcessingException ex,
                                                      HttpServletRequest req) {
    var body = new ErrorResponse(
        ex.getStatus().getReasonPhrase(),
        ex.getCode(),
        ex.getMessage(),
        req.getRequestURI(),
        Instant.now()
    );
    return ResponseEntity.status(ex.getStatus()).body(body);
  }
}
