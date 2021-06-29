package es.parravidales.tasks.utils;

import es.parravidales.tasks.repository.TaskEntity;

import java.util.UUID;

import static es.parravidales.tasks.utils.RandomUtils.nextLong;

public class EntityUtils {

  public static TaskEntity buildTaskEntity(boolean complete) {
    return TaskEntity.builder()
      .id(nextLong())
      .title(UUID.randomUUID().toString())
      .description(UUID.randomUUID().toString())
      .complete(complete)
      .build();
  }
}
