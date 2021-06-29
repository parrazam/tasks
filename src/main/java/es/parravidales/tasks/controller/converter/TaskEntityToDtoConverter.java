package es.parravidales.tasks.controller.converter;

import es.parravidales.tasks.controller.dto.TaskDto;
import es.parravidales.tasks.repository.TaskEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class TaskEntityToDtoConverter implements Converter<TaskEntity, TaskDto> {

  @Override
  public TaskDto convert(TaskEntity taskEntity) {
    if (nonNull(taskEntity)) {
      TaskDto target = new TaskDto();
      target.setId(taskEntity.getId());
      target.setTitle(taskEntity.getTitle());
      target.setDescription(taskEntity.getDescription());
      target.setComplete(taskEntity.isComplete());

      return target;
    }
    return null;
  }
}
