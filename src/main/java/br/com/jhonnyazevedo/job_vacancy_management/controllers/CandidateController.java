package br.com.jhonnyazevedo.job_vacancy_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Candidate;
import br.com.jhonnyazevedo.job_vacancy_management.repositories.CandidateRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CandidateRepository repository;

  @PostMapping("/")
  public Candidate create(@Valid @RequestBody Candidate candidate) {
    this.repository.save(candidate);

    return candidate;
}

}
