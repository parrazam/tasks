package es.parravidales.tasks.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@NoArgsConstructor
public class TaskDto {

  @NonNull
  long id;

  @NonNull
  String title;

  String description;

  boolean complete;

}
