package org.example.indexer.api.controller;

import org.example.indexer.api.service.IndexService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {
  private final IndexService service;

  public IndexController(IndexService service) {
    this.service = service;
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, Object> index(@RequestPart("file") @NotNull MultipartFile file) {
    validate(file);
    try {
      return service.index(file.getInputStream());
    } catch (Exception e) {
      throw new RuntimeException("Could not read uploaded file", e);
    }
  }

  private void validate(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new RuntimeException("INDEX_MISSING_FILE Required part 'file' is missing or empty");
    }
  }
}
