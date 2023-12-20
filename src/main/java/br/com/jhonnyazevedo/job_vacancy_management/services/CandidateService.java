package br.com.jhonnyazevedo.job_vacancy_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Candidate;
import br.com.jhonnyazevedo.job_vacancy_management.exceptions.UserFoundException;
import br.com.jhonnyazevedo.job_vacancy_management.repositories.CandidateRepository;


@Service
public class CandidateService {
  
  @Autowired
  CandidateRepository repository;

  public Candidate execute(Candidate candidate) {
    // Verifica se não existe um usuário com mesmo username ou email cadastrado
    this.repository
    .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
    .ifPresent((user) -> {
      throw new UserFoundException();
    });


    this.repository.save(candidate);

    return candidate;
  }
}
