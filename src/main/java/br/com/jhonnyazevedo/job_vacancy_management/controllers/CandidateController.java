package br.com.jhonnyazevedo.job_vacancy_management.controllers;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Job;
import br.com.jhonnyazevedo.job_vacancy_management.services.ListAllJobsByFilterService;
import br.com.jhonnyazevedo.job_vacancy_management.services.ProfileCandidateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Candidate;
import br.com.jhonnyazevedo.job_vacancy_management.services.CandidateService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CandidateService service;

  @Autowired
  private ProfileCandidateService profileCandidateService;

  @Autowired
  private ListAllJobsByFilterService listAllJobsByFilterService;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody Candidate candidate) {

    try {
      var result = this.service.execute(candidate);
      return ResponseEntity.ok().body(result);
      
    } catch(Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")// só quem tem a role "candidate" terá acesso ao endpoint pelo SpringSecurity
  public ResponseEntity<Object> get(HttpServletRequest request) {

    var idCandidate = request.getAttribute("candidate_id");

    try {
      var profile = this.profileCandidateService.execute(UUID.fromString(idCandidate.toString()));
      return ResponseEntity.ok().body(profile);

    } catch(Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/job")
  @PreAuthorize("hasRole('CANDIDATE')")// só quem tem a role "candidate" terá acesso ao endpoint pelo SpringSecurity
  public List<Job> findJobsByFilter(@RequestParam String filter) {
    return this.listAllJobsByFilterService.execute(filter);
  }



}
