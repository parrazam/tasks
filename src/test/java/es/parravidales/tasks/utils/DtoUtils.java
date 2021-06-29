package es.parravidales.tasks.utils;

import es.parravidales.tasks.controller.dto.NewTaskDto;
import es.parravidales.tasks.controller.dto.TaskDto;

import java.util.UUID;

import static es.parravidales.tasks.utils.RandomUtils.nextLong;

public class DtoUtils {

  public static NewTaskDto buildNewTaskDto() {
    NewTaskDto newTaskDto = new NewTaskDto();
    newTaskDto.setTitle(UUID.randomUUID().toString());
    newTaskDto.setDescription(UUID.randomUUID().toString());

    return newTaskDto;
  }

  public static TaskDto buildTaskDto() {
    TaskDto taskDto = new TaskDto();
    taskDto.setId(nextLong());
    taskDto.setTitle(UUID.randomUUID().toString());
    taskDto.setDescription(UUID.randomUUID().toString());
    taskDto.setComplete(true);

    return taskDto;
  }
}
