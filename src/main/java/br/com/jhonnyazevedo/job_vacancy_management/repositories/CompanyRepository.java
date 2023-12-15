package br.com.jhonnyazevedo.job_vacancy_management.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
  
  Optional<Company> findByUsernameOrEmail(String username, String email);
}
