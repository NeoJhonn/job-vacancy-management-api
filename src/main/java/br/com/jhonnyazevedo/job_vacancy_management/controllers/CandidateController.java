package br.com.jhonnyazevedo.job_vacancy_management.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Candidate;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @PostMapping("/")
  public void create(@RequestBody Candidate candidate) {
    System.out.println("Candidato");
    System.out.println(candidate.getEmail());
  }
  
}
