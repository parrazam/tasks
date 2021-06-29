package es.parravidales.tasks.controller.converter;

import es.parravidales.tasks.controller.dto.TaskDto;
import es.parravidales.tasks.repository.TaskEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class TaskDtoToTaskEntityConverter implements Converter<TaskDto, TaskEntity> {

  @Override
  public TaskEntity convert(TaskDto taskDto) {
    if (nonNull(taskDto)) {
      return TaskEntity.builder()
        .id(taskDto.getId())
        .title(taskDto.getTitle())
        .description(taskDto.getDescription())
        .complete(taskDto.isComplete())
        .build();
    }
    return null;
  }
}
