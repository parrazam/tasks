package es.parravidales.tasks.controller.converter;

import es.parravidales.tasks.controller.dto.TaskDto;
import es.parravidales.tasks.repository.TaskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static es.parravidales.tasks.utils.EntityUtils.buildTaskEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class TaskEntityToDtoConverterTest {

  TaskEntityToDtoConverter sut;

  @BeforeEach
  public void before() {
    sut = new TaskEntityToDtoConverter();
  }

  @Test
  public void testConvertEntityToDto() {
    TaskEntity source = buildTaskEntity(true);
    TaskDto target = sut.convert(source);
    assertEquals(source.getId(), target.getId());
    assertEquals(source.getTitle(), target.getTitle());
    assertEquals(source.getDescription(), target.getDescription());
    assertEquals(source.isComplete(), target.isComplete());
  }

  @Test
  public void testConvertNullToDto() {
    assertNull(sut.convert(null));
  }

}