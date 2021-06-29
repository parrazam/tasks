package es.parravidales.tasks.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@NoArgsConstructor
public class NewTaskDto {

  @NonNull
  String title;

  String description;
}
