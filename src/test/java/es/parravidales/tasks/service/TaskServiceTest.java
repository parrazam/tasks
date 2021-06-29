package es.parravidales.tasks.service;

import es.parravidales.tasks.exceptions.TaskNotFoundException;
import es.parravidales.tasks.repository.TaskEntity;
import es.parravidales.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static es.parravidales.tasks.utils.EntityUtils.buildTaskEntity;
import static es.parravidales.tasks.utils.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

  @Mock
  TaskRepository repository;

  @InjectMocks
  TaskService sut;

  @Test
  public void testGetAllTasks() {
    List<TaskEntity> tasks = List.of(buildTaskEntity(false), buildTaskEntity(true));
    given(repository.findAll()).willReturn(tasks);

    assertEquals(tasks, sut.getAllTasks());

    then(repository).should().findAll();
  }

  @Test
  public void testGetAllCompletedTasks() {
    List<TaskEntity> completedTasks = List.of(buildTaskEntity(true), buildTaskEntity(true));
    given(repository.findByComplete(true)).willReturn(completedTasks);

    assertEquals(completedTasks, sut.getAllCompletedTasks());

    then(repository).should().findByComplete(true);
    then(repository).should(never()).findByComplete(false);
  }

  @Test
  public void testGetExistingTask() {
    TaskEntity expectedTask = buildTaskEntity(false);
    given(repository.findById(expectedTask.getId())).willReturn(Optional.of(expectedTask));

    assertEquals(expectedTask, sut.getTask(expectedTask.getId()));
  }

  @Test
  public void testNotFoundTask() {
    given(repository.findById(anyLong())).willReturn(Optional.empty());

    assertThrows(TaskNotFoundException.class, () -> sut.getTask(nextLong()));
  }

  @Test
  public void testSaveNewTask() {
    TaskEntity task = buildTaskEntity(false);
    given(repository.save(task)).willReturn(task);

    assertEquals(task.getId(), sut.createTask(task));
  }

  @Test
  public void testDeleteTask() {
    long expectedId = nextLong();
    willDoNothing().given(repository).deleteById(expectedId);

    sut.deleteTask(expectedId);
    then(repository).should().deleteById(expectedId);
  }

  @Test
  public void testDeleteCompletedTasks() {
    List<TaskEntity> completedTasks = List.of(buildTaskEntity(true), buildTaskEntity(true));
    given(repository.findByComplete(true)).willReturn(completedTasks);

    sut.deleteCompletedTasks();
    then(repository).should().findByComplete(true);
    completedTasks.forEach(deletedTask -> {
      then(repository).should().delete(deletedTask);
    });
  }

  @Test
  public void testUpdateExistingTask() {
    TaskEntity expectedNewTask = buildTaskEntity(false);
    long expectedId = nextLong();
    given(repository.existsById(expectedId)).willReturn(true);
    given(repository.save(expectedNewTask)).willReturn(expectedNewTask);

    assertNotEquals(expectedId, expectedNewTask.getId());
    assertEquals(expectedId, sut.updateTask(expectedId, expectedNewTask));
    assertEquals(expectedId, expectedNewTask.getId());

    then(repository).should().existsById(expectedId);
    then(repository).should().save(expectedNewTask);
  }

  @Test
  public void testUpdateNonExistingTask() {
    TaskEntity expectedNewTask = buildTaskEntity(false);
    long expectedId = nextLong();
    given(repository.existsById(expectedId)).willReturn(false);

    assertThrows(TaskNotFoundException.class, () -> sut.updateTask(expectedId, expectedNewTask));

    then(repository).should().existsById(expectedId);
    then(repository).should(never()).save(any(TaskEntity.class));
  }

  @Test
  public void testMarkTaskAsComplete() {
    TaskEntity expectedTask = buildTaskEntity(false);
    given(repository.findById(expectedTask.getId())).willReturn(Optional.of(expectedTask));

    assertFalse(expectedTask.isComplete());
    sut.markAsComplete(expectedTask.getId());
    assertTrue(expectedTask.isComplete());

    then(repository).should().findById(expectedTask.getId());
    then(repository).should().save(expectedTask);
  }

  @Test
  public void testMarkTaskAsCompleteNonExistingTask() {
    long expectedId = nextLong();
    given(repository.findById(expectedId)).willReturn(Optional.empty());

    assertThrows(TaskNotFoundException.class, () -> sut.markAsComplete(expectedId));

    then(repository).should().findById(expectedId);
    then(repository).should(never()).save(any(TaskEntity.class));
  }

}