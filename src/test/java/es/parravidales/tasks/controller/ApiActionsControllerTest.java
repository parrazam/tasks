package es.parravidales.tasks.controller;

import es.parravidales.tasks.repository.TaskEntity;
import es.parravidales.tasks.utils.BaseItTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApiActionsControllerTest extends BaseItTest {

  final String BASE_ENDPOINT = "/task";

  @Test
  @SneakyThrows
  public void testCompleteTaskAction() {

    String endpoint = BASE_ENDPOINT + "/{id}/complete";
    given(repository.findById(0L))
      .willReturn(Optional.of(TaskEntity.builder().id(0L).title(UUID.randomUUID().toString()).build()));

    mockMvc.perform(post(endpoint, 0L)
      .contentType(MediaType.TEXT_PLAIN))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  public void testCompleteTaskActionForNonExistingTask() {

    String endpoint = BASE_ENDPOINT + "/{id}/complete";
    given(repository.findById(0L))
      .willReturn(Optional.empty());

    mockMvc.perform(post(endpoint, 0L)
      .contentType(MediaType.TEXT_PLAIN))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

}