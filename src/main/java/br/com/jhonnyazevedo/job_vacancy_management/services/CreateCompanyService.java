package br.com.jhonnyazevedo.job_vacancy_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Company;
import br.com.jhonnyazevedo.job_vacancy_management.exceptions.UserFoundException;
import br.com.jhonnyazevedo.job_vacancy_management.repositories.CompanyRepository;

@Service
public class CreateCompanyService {

  @Autowired
  private CompanyRepository repository;
  
  public Company execute(Company company) {
    // Verifica se não existe um usuário com mesmo username ou email cadastrado
    this.repository
    .findByUsernameOrEmail(company.getUsername(), company.getEmail())
    .ifPresent((user) -> {
      throw new UserFoundException();
    });

    return this.repository.save(company);
  }
}
