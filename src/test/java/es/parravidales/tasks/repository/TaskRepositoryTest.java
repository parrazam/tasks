package es.parravidales.tasks.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@EnableJpaAuditing
public class TaskRepositoryTest {

  @Autowired
  private TaskRepository sut;

  @Test
  public void testFindByComplete() {
    sut.save(buildRandomTaskEntity(true));
    sut.save(buildRandomTaskEntity(false));
    assertEquals(2, sut.findAll().size());
    assertEquals(1, sut.findByComplete(true).size());
    assertEquals(1, sut.findByComplete(false).size());
  }

  private TaskEntity buildRandomTaskEntity(boolean complete) {
    return TaskEntity.builder()
      .title(UUID.randomUUID().toString())
      .complete(complete)
      .build();
  }
}
