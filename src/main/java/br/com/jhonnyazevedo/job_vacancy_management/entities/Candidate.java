package br.com.jhonnyazevedo.job_vacancy_management.entities;

import java.util.UUID;

import lombok.Data;

@Data
public class Candidate {
  
  private UUID id;
  private String name;
  private String username;
  private String email;
  private String password;
  private String description;
  private String resume;

}
