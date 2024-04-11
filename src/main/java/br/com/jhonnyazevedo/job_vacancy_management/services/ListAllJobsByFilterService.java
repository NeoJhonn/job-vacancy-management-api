package br.com.jhonnyazevedo.job_vacancy_management.services;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Job;
import br.com.jhonnyazevedo.job_vacancy_management.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByFilterService {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> execute(String filter) {
        return this.jobRepository.findByDescriptionContaining(filter);
    }
}
