package es.parravidales.tasks.repository;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Builder
@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Size(max = 20)
  @NonNull
  private String title;

  @Size(max = 1024)
  private String description;

  private boolean complete;
}
