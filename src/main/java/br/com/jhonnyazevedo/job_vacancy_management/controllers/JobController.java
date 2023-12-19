package br.com.jhonnyazevedo.job_vacancy_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Job;
import br.com.jhonnyazevedo.job_vacancy_management.services.CreateJobService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
public class JobController {

  @Autowired
  private CreateJobService services;

  @PostMapping("/")
  public Job create(@Valid @RequestBody Job job) {
    return this.services.execute(job);
  }
  
}
