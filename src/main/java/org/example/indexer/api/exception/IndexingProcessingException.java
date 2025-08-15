package org.example.indexer.api.exception;

import org.springframework.http.HttpStatus;

public class IndexingProcessingException extends RuntimeException {
  private final String code;
  private final HttpStatus status;

  public IndexingProcessingException(HttpStatus status, String code, String message) {
    super(message);
    this.status = status;
    this.code = code;
  }

  public IndexingProcessingException(HttpStatus status, String code, String message, Throwable cause) {
    super(message, cause);
    this.status = status;
    this.code = code;
  }

  public String getCode() { return code; }
  public HttpStatus getStatus() { return status; }


  public static IndexingProcessingException badRequest(String code, String message) {
    return new IndexingProcessingException(HttpStatus.BAD_REQUEST, code, message);
  }
  public static IndexingProcessingException internal(String code, String message, Throwable cause) {
    return new IndexingProcessingException(HttpStatus.INTERNAL_SERVER_ERROR, code, message, cause);
  }
}
