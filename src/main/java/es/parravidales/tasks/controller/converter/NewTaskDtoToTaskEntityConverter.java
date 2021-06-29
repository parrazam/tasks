package es.parravidales.tasks.controller.converter;

import es.parravidales.tasks.controller.dto.NewTaskDto;
import es.parravidales.tasks.repository.TaskEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class NewTaskDtoToTaskEntityConverter implements Converter<NewTaskDto, TaskEntity> {

  @Override
  public TaskEntity convert(NewTaskDto taskDto) {
    if (nonNull(taskDto)) {
      return TaskEntity.builder()
              .title(taskDto.getTitle())
              .description(taskDto.getDescription())
              .build();
    }
    return null;
  }
}
