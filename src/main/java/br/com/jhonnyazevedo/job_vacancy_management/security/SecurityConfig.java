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
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
          // permitir que essas rotas da api sejam acessadas sem autenticação
          auth.requestMatchers("/candidate/").permitAll()
              .requestMatchers("/company/").permitAll();

          // para as demais rotas, o caso a rota "/job/" será necessário autenticação
          auth.anyRequest().authenticated();
        })


    ;

    return http.build();
  }
  
}
