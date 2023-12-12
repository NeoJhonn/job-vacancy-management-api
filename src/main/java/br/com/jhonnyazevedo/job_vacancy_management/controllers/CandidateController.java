package br.com.jhonnyazevedo.job_vacancy_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Candidate;
import br.com.jhonnyazevedo.job_vacancy_management.services.CreateCandidateService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CreateCandidateService service;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody Candidate candidate) {

    try {
      var result = this.service.execute(candidate);
      return ResponseEntity.ok().body(result);
      
    } catch(Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    
}

}
