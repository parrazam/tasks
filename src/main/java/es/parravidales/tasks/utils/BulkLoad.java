package es.parravidales.tasks.utils;

import es.parravidales.tasks.repository.TaskEntity;
import es.parravidales.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "tasks", name = "bulkload")
public class BulkLoad {

  private final TaskRepository taskRepository;

  @PostConstruct
  public void postConstruct() {
    Random random = new Random();
    IntStream.range(0, 100).forEach(i -> {
      log.debug("Saving random entity {}", i);
      taskRepository.save(TaskEntity.builder()
        .title("Task " + i)
        .description(UUID.randomUUID().toString())
        .complete(random.nextBoolean())
        .build());
    });
  }
}
