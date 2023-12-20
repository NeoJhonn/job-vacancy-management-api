package br.com.jhonnyazevedo.job_vacancy_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Job;
import br.com.jhonnyazevedo.job_vacancy_management.repositories.JobRepository;

@Service
public class JobService {

  @Autowired
  private JobRepository repository;

  public Job execute(Job job) {
    return this.repository.save(job);
  }
  
}
