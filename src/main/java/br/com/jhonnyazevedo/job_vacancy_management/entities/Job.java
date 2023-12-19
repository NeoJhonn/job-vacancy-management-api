package br.com.jhonnyazevedo.job_vacancy_management.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity(name = "job")
@Data
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String description;

  @NotBlank(message = "Esse campo é obrigatório.")
  private String level;

  private String benefits;

  @ManyToOne
  // (insertable = false, updatable = false) --> O ORM vai entender que vai ser usado para 
  // manipulações de dados o companyId de baixo
  @JoinColumn(name = "company_id", insertable = false, updatable = false)
  private Company company;

  @Column(name ="company_id", nullable = false)
  private UUID companyId;

  @CreationTimestamp
  private LocalDateTime createdAt;

  
}
