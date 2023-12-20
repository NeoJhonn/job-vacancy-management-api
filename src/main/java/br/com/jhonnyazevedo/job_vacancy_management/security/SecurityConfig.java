package br.com.jhonnyazevedo.job_vacancy_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean // notação que sobrescreve o méto original do Spring Security
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // desabilita o Spring Security na aplicação para poder fazer a segurança personalizada
    // do meu jeito
    http.csrf(csrf -> csrf.disable());

    return http.build();
  }
  
}
