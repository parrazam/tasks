package es.parravidales.tasks.controller.converter;

import es.parravidales.tasks.controller.dto.NewTaskDto;
import es.parravidales.tasks.repository.TaskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static es.parravidales.tasks.utils.DtoUtils.buildNewTaskDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class NewTaskDtoToTaskEntityConverterTest {

  NewTaskDtoToTaskEntityConverter sut;

  @BeforeEach
  public void before() {
    sut = new NewTaskDtoToTaskEntityConverter();
  }

  @Test
  public void testConvertDtoToEntity() {
    NewTaskDto source = buildNewTaskDto();
    TaskEntity target = sut.convert(source);
    assertEquals(source.getTitle(), target.getTitle());
    assertEquals(source.getDescription(), target.getDescription());
    assertEquals(0, target.getId());
    assertNull(target.getCreatedDate());
    assertNull(target.getLastModifiedDate());
  }

  @Test
  public void testConvertNullValue() {
    assertNull(sut.convert(null));
  }

}