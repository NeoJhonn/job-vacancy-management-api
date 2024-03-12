package br.com.jhonnyazevedo.job_vacancy_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jhonnyazevedo.job_vacancy_management.entities.Company;
import br.com.jhonnyazevedo.job_vacancy_management.exceptions.UserFoundException;
import br.com.jhonnyazevedo.job_vacancy_management.repositories.CompanyRepository;

@Service
public class CompanyService {

  @Autowired
  private CompanyRepository repository;

  @Autowired // foi configurado em SecurityConfig
  PasswordEncoder passwordEncoder;
  
  public Company execute(Company company) {
    // Verifica se não existe um usuário com mesmo username ou email cadastrado
    this.repository
    .findByUsernameOrEmail(company.getUsername(), company.getEmail())
    .ifPresent((user) -> {
      throw new UserFoundException();
    });

    // criptografar senha da Company
    var password = passwordEncoder.encode(company.getPassword());
    company.setPassword(password);

    return this.repository.save(company);
  }
}
