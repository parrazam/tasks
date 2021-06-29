package es.parravidales.tasks.repository;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity implements Serializable {

  @CreatedDate
  @Column(name = "insert_timestamp", updatable = false, nullable = false)
  protected LocalDateTime createdDate;

  @LastModifiedDate
  @Column(name = "update_timestamp", nullable = false)
  protected LocalDateTime lastModifiedDate;
}
