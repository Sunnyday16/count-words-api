
import org.example.indexer.api.controller.IndexController;
import org.example.indexer.api.service.IndexService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IndexControllerTest {

  private MockMvc mockMvc;
  private IndexService service;

  @BeforeEach
  void setUp() {
    service = Mockito.mock(IndexService.class);
    mockMvc = MockMvcBuilders.standaloneSetup(new IndexController(service)).build();
  }


  @Test
  void returnsJsonOnUpload() throws Exception {
    var file = new MockMultipartFile("file", "input.txt", "text/plain",
        "Many modern systems, like microservices".getBytes());

    Mockito.when(service.index(Mockito.any()))
        .thenReturn(Map.of(
            "countStartingWithM", 3,
            "wordsLongerThan5", java.util.List.of("modern","systems","microservices")
        ));

    mockMvc.perform(
        multipart("/index").file(file).accept(MediaType.APPLICATION_JSON)
    )
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.countStartingWithM").value(3))
      .andExpect(jsonPath("$.wordsLongerThan5[0]").value("modern"));
  }

}
