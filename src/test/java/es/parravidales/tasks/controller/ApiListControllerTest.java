package es.parravidales.tasks.controller;

import es.parravidales.tasks.repository.TaskEntity;
import es.parravidales.tasks.utils.BaseItTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ApiListControllerTest extends BaseItTest {

  final String BASE_ENDPOINT = "/tasks";

  @Test
  @SneakyThrows
  public void testGetAllTasks() {

    given(repository.findAll())
      .willReturn(List.of(
        TaskEntity.builder().id(0L).title(UUID.randomUUID().toString()).build(),
        TaskEntity.builder().id(1L).title(UUID.randomUUID().toString()).build(),
        TaskEntity.builder().id(2L).title(UUID.randomUUID().toString()).build()));

    mockMvc.perform(get(BASE_ENDPOINT))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$..[?(@.id==0)].id").value(0))
      .andExpect(jsonPath("$..[?(@.id==1)].id").value(1))
      .andExpect(jsonPath("$..[?(@.id==2)].id").value(2))
      .andReturn();

    then(repository).should().findAll();
  }

  @Test
  @SneakyThrows
  public void testGetAllCompletedTasks() {

    given(repository.findByComplete(true))
      .willReturn(List.of(
        TaskEntity.builder().id(0L).complete(true).title(UUID.randomUUID().toString()).build(),
        TaskEntity.builder().id(1L).complete(true).title(UUID.randomUUID().toString()).build(),
        TaskEntity.builder().id(2L).complete(true).title(UUID.randomUUID().toString()).build()));

    mockMvc.perform(get(BASE_ENDPOINT + "/completed"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$..[?(@.id==0)].id").value(0))
      .andExpect(jsonPath("$..[?(@.id==1)].id").value(1))
      .andExpect(jsonPath("$..[?(@.id==2)].id").value(2))
      .andReturn();

    then(repository).should().findByComplete(true);
    then(repository).should(never()).findByComplete(false);
  }

  @Test
  @SneakyThrows
  public void testDeleteAllCompletedTasks() {

    given(repository.findByComplete(true))
      .willReturn(List.of(
        TaskEntity.builder().id(0L).complete(true).title(UUID.randomUUID().toString()).build(),
        TaskEntity.builder().id(1L).complete(true).title(UUID.randomUUID().toString()).build(),
        TaskEntity.builder().id(2L).complete(true).title(UUID.randomUUID().toString()).build()));
    willDoNothing().given(repository).delete(any(TaskEntity.class));

    mockMvc.perform(delete(BASE_ENDPOINT + "/completed"))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();

    then(repository).should().findByComplete(true);
    then(repository).should(never()).findByComplete(false);
    then(repository).should(times(3)).delete(any(TaskEntity.class));
  }

}