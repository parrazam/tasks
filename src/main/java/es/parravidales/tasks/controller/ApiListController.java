package es.parravidales.tasks.controller;

import es.parravidales.tasks.controller.dto.TaskDto;
import es.parravidales.tasks.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Tag(name = "Lists", description = "Bulk operations with tasks")
public class ApiListController {

  private final TaskService taskService;

  private final ConversionService conversionService;

  @Operation(summary = "Get all tasks")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "List of all tasks",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
        array = @ArraySchema(schema = @Schema(implementation = TaskDto.class)))}),
    @ApiResponse(responseCode = "500", description = "Internal error",
      content = @Content)
  })
  @GetMapping("/tasks")
  public ResponseEntity<List<TaskDto>> getAllTasks() {
    return ResponseEntity.ok(taskService.getAllTasks().stream()
      .map(taskEntity -> conversionService.convert(taskEntity, TaskDto.class))
      .collect(Collectors.toList()));
  }

  @Operation(summary = "Get all completed tasks")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "List of all completed tasks",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
        array = @ArraySchema(schema = @Schema(implementation = TaskDto.class)))}),
    @ApiResponse(responseCode = "500", description = "Internal error",
      content = @Content)
  })
  @GetMapping("/tasks/completed")
  public ResponseEntity<List<TaskDto>> getCompletedTasks() {
    return ResponseEntity.ok(taskService.getAllCompletedTasks().stream()
      .map(taskEntity -> conversionService.convert(taskEntity, TaskDto.class))
      .collect(Collectors.toList()));
  }

  @Operation(summary = "Delete all completed tasks")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Deleted successful",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Internal error",
      content = @Content)
  })
  @DeleteMapping("/tasks/completed")
  public void deleteCompletedTasks() {
    taskService.deleteCompletedTasks();
  }

}
