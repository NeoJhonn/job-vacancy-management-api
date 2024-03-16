package br.com.jhonnyazevedo.job_vacancy_management.controllers;

import br.com.jhonnyazevedo.job_vacancy_management.dtos.JobDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Job;
import br.com.jhonnyazevedo.job_vacancy_management.services.JobService;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

  @Autowired
  private JobService services;

  @Autowired
  ModelMapper mapper;

  @PostMapping("/")
  public Job create(@Valid @RequestBody JobDTO jobDTO, HttpServletRequest request) {
    // Pega o id da company que esta autenticada
    var companyId = request.getAttribute("company_id");

    // seta o id da company no job que for salvar

    // Settando com builder
    //    var job = Job.builder()
    // .description(jobDTO.getDescription())
    // .benefits(jobDTO.getBenefits())
    // .level(jobDTO.getLevel())
    // .companyId(UUID.fromString(companyId.toString()))
    // .build();

    // Settando com ModelMapper
    var job = mapper.map(jobDTO, Job.class);
    job.setCompanyId(UUID.fromString(companyId.toString()));

    return this.services.execute(job);
  }
  
}
