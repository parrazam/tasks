package es.parravidales.tasks.exceptions;

public class TaskNotFoundException extends RuntimeException {

  public TaskNotFoundException() {
    super("Unable to find task");
  }
}
