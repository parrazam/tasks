package es.parravidales.tasks.service;

import es.parravidales.tasks.exceptions.TaskNotFoundException;
import es.parravidales.tasks.repository.TaskEntity;
import es.parravidales.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  public List<TaskEntity> getAllTasks() {
    return taskRepository.findAll();
  }

  public List<TaskEntity> getAllCompletedTasks() {
    return taskRepository.findByComplete(true);
  }

  public TaskEntity getTask(long id) {
    return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException());
  }

  public long createTask(TaskEntity newTask) {
    return taskRepository.save(newTask).getId();
  }

  public void deleteTask(long id) {
    taskRepository.deleteById(id);
  }

  public void deleteCompletedTasks() {
    taskRepository.findByComplete(true).forEach(taskRepository::delete);
  }

  public long updateTask(long id, TaskEntity task) {
    if (taskRepository.existsById(id)) {
      task.setId(id);
      return taskRepository.save(task).getId();
    }
    throw new TaskNotFoundException();
  }

  public void markAsComplete(long id) {
    TaskEntity task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException());
    task.setComplete(true);
    taskRepository.save(task);
    return;
  }
}
