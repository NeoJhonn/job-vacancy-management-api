package br.com.jhonnyazevedo.job_vacancy_management.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Job;

public interface JobRepository extends JpaRepository<Job, UUID> {

    // contain = LIKE do SQL
    // Select * from job where description like %filter%
    List<Job> findByDescriptionContaining(String filter);
}
