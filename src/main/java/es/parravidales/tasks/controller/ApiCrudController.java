package es.parravidales.tasks.controller;

import es.parravidales.tasks.controller.dto.NewTaskDto;
import es.parravidales.tasks.controller.dto.TaskDto;
import es.parravidales.tasks.repository.TaskEntity;
import es.parravidales.tasks.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "API for CRUD events")
public class ApiCrudController {

  private final TaskService taskService;

  private final ConversionService conversionService;

  @Operation(summary = "Get tasks with details")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Detailed task",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = TaskDto.class))}),
    @ApiResponse(responseCode = "404", description = "Task not found",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Internal error",
      content = @Content)
  })
  @GetMapping("/task/{id}")
  public ResponseEntity<TaskDto> getTask(@PathVariable long id) {
    return ResponseEntity.ok(
      conversionService.convert(taskService.getTask(id), TaskDto.class));
  }

  @Operation(summary = "Create new task")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Detailed task",
      content = {@Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
        schema = @Schema(implementation = Long.class))}),
    @ApiResponse(responseCode = "400", description = "Missing required fields",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Internal error",
      content = @Content)
  })
  @PostMapping("/task")
  public ResponseEntity<String> createTask(@RequestBody NewTaskDto newTask) {
    log.debug("Creating new task...");
    return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.TEXT_PLAIN).body(
      String.valueOf(
        taskService.createTask(conversionService.convert(newTask, TaskEntity.class))));
  }

  @Operation(summary = "Delete a task")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Deleted",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Internal error",
      content = @Content)
  })
  @DeleteMapping("/task/{id}")
  public void deleteTask(@PathVariable long id) {
    taskService.deleteTask(id);
  }

  @Operation(summary = "Update an existing task")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Updated",
      content = {@Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
        schema = @Schema(implementation = Long.class))}),
    @ApiResponse(responseCode = "404", description = "Task not found",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Internal error",
      content = @Content)
  })
  @PutMapping("/task/{id}")
  public ResponseEntity<String> updateTask(@PathVariable long id, @RequestBody NewTaskDto task) {
    return ResponseEntity.ok(
      String.valueOf(taskService.updateTask(id, conversionService.convert(task, TaskEntity.class))));
  }

}
