package es.parravidales.tasks.controller;

import es.parravidales.tasks.repository.TaskEntity;
import es.parravidales.tasks.utils.BaseItTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Optional;

import static es.parravidales.tasks.utils.EntityUtils.buildTaskEntity;
import static es.parravidales.tasks.utils.RandomUtils.nextLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ApiCrudControllerTest extends BaseItTest {

  final String BASE_ENDPOINT = "/task";

  @Test
  @SneakyThrows
  public void testGetTask() {

    TaskEntity expectedTask = buildTaskEntity(false);
    given(repository.findById(expectedTask.getId())).willReturn(Optional.of(expectedTask));

    mockMvc.perform(get(BASE_ENDPOINT + "/" + expectedTask.getId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("id").value(expectedTask.getId()))
      .andExpect(jsonPath("title").value(expectedTask.getTitle()))
      .andExpect(jsonPath("description").value(expectedTask.getDescription()));

    then(repository).should().findById(expectedTask.getId());
  }

  @Test
  @SneakyThrows
  public void testGetNonExistingTask() {

    TaskEntity expectedTask = buildTaskEntity(false);
    given(repository.findById(expectedTask.getId())).willReturn(Optional.empty());

    mockMvc.perform(get(BASE_ENDPOINT + "/" + expectedTask.getId()))
      .andDo(print())
      .andExpect(status().isNotFound());

    then(repository).should().findById(expectedTask.getId());
  }

  @Test
  @SneakyThrows
  public void testCreateTask() {

    TaskEntity expectedTask = buildTaskEntity(false);
    given(repository.save(any(TaskEntity.class))).willReturn(expectedTask);

    mockMvc.perform(post(BASE_ENDPOINT)
      .content("{\"title\": \"Test\"}")
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isCreated());

    then(repository).should().save(any(TaskEntity.class));
  }

  @Test
  @SneakyThrows
  public void testCreateTaskWithoutTitle() {

    mockMvc.perform(post(BASE_ENDPOINT)
      .content("{\"description\": \"DescriptionTest\"}")
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isBadRequest());

    then(repository).should(never()).save(any(TaskEntity.class));
  }

  @Test
  @SneakyThrows
  public void testDeleteTask() {

    long expectedId = nextLong();
    willDoNothing().given(repository).deleteById(expectedId);

    mockMvc.perform(delete(BASE_ENDPOINT + "/" + expectedId))
      .andDo(print())
      .andExpect(status().isOk());

    then(repository).should().deleteById(expectedId);
  }

  @Test
  @SneakyThrows
  public void testUpdateTask() {

    TaskEntity expectedTask = buildTaskEntity(false);
    given(repository.existsById(expectedTask.getId())).willReturn(true);
    given(repository.save(any(TaskEntity.class))).willReturn(expectedTask);

    mockMvc.perform(put(BASE_ENDPOINT + "/{id}", expectedTask.getId())
      .content("{" +
        "\"title\": \"" + expectedTask.getTitle() + "\", " +
        "\"description\": \"" + expectedTask.getDescription() + "\"}")
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk());

    then(repository).should().existsById(expectedTask.getId());
    then(repository).should().save(expectedTask);
  }

  @Test
  @SneakyThrows
  public void testUpdateNonExistingTask() {

    TaskEntity expectedTask = buildTaskEntity(false);
    given(repository.existsById(expectedTask.getId())).willReturn(false);

    mockMvc.perform(put(BASE_ENDPOINT + "/{id}", expectedTask.getId())
      .content("{" +
        "\"title\": \"" + expectedTask.getTitle() + "\", " +
        "\"description\": \"" + expectedTask.getDescription() + "\"}")
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());

    then(repository).should().existsById(expectedTask.getId());
    then(repository).should(never()).save(expectedTask);
  }

}