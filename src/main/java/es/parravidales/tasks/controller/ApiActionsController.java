package es.parravidales.tasks.controller;

import es.parravidales.tasks.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Tag(name = "Actions", description = "Actions with individual tasks")
public class ApiActionsController {

  private final TaskService taskService;

  @Operation(summary = "Mark task as complete")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Completed",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Task not found",
      content = @Content),
    @ApiResponse(responseCode = "500", description = "Internal error",
      content = @Content)
  })
  @PostMapping("/task/{id}/complete")
  public void completeTask(@PathVariable long id) {
    taskService.markAsComplete(id);
  }

}
