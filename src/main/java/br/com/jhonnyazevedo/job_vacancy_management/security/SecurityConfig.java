package br.com.jhonnyazevedo.job_vacancy_management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCompanyFilter securityCompanyFilter;

    @Autowired
    private SecurityCandidateFilter securityCandidateFilter;

  @Bean // notação que sobrescreve o método original do Spring Security
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // desabilita o Spring Security na aplicação para poder fazer a segurança personalizada
    // do meu jeito
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
          // permitir que essas rotas da api sejam acessadas sem autenticação
          auth.requestMatchers("/company/").permitAll()
              .requestMatchers("/candidate/").permitAll()
              .requestMatchers("/candidate/auth").permitAll()
              .requestMatchers("/company/auth").permitAll();

          // para as demais rotas, no caso a rota "/job/" será necessário autenticação
          auth.anyRequest().authenticated();
        })

        // Adiconar filtros nos endpoints que necessitam autenticação
        // passa como parâmetro o filtro que criamos, no caso a classe
        // securityCandidateFilter
        .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)

        // Adiconar filtros nos endpoints que necessitam autenticação
        // passa como parâmetro o filtro que criamos, o caso a classe securityFilter
        .addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class);

      return http.build();
  }

  @Bean // criptografar senhas
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
}
