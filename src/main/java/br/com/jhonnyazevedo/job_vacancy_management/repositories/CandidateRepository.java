package br.com.jhonnyazevedo.job_vacancy_management.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

  Optional<Candidate> findByUsernameOrEmail(String username, String email);

  Optional<Candidate> findByUsername(String username);

}
