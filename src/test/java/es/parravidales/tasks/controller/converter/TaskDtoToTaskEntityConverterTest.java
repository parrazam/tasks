package es.parravidales.tasks.controller.converter;

import es.parravidales.tasks.controller.dto.TaskDto;
import es.parravidales.tasks.repository.TaskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static es.parravidales.tasks.utils.DtoUtils.buildTaskDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(MockitoExtension.class)
class TaskDtoToTaskEntityConverterTest {

  TaskDtoToTaskEntityConverter sut;

  @BeforeEach
  public void before() {
    sut = new TaskDtoToTaskEntityConverter();
  }

  @Test
  public void testConvertDtoToEntity() {
    TaskDto source = buildTaskDto();
    TaskEntity target = sut.convert(source);
    assertEquals(source.getTitle(), target.getTitle());
    assertEquals(source.getDescription(), target.getDescription());
    assertEquals(source.getId(), target.getId());
    assertEquals(source.isComplete(), target.isComplete());
    assertNull(target.getCreatedDate());
    assertNull(target.getLastModifiedDate());
  }

  @Test
  public void testConvertNullValue() {
    assertNull(sut.convert(null));
  }

}