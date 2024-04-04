package br.com.jhonnyazevedo.job_vacancy_management.security;

import br.com.jhonnyazevedo.job_vacancy_management.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component// o filter vai ser um component pro Spring consegui gerenciar o ciclo de vida dele
public class SecurityCandidateFilter extends OncePerRequestFilter {

    @Autowired
    private JWTCandidateProvider jwtCandidateProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Zerar autenticações anteriores
        //SecurityContextHolder.getContext().setAuthentication(null);

        String header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/candidate")) {

            if (header != null) {
                var token = this.jwtCandidateProvider.validateToken(header);

                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                request.setAttribute("candidate_id", token.getSubject());

                var roles = token.getClaim("role").asList(Object.class);

                // cria uma lista de roles
                // Por padrão o Spring cria lista de roles com o prefixo "ROLE_" + "NOMEROLE
                var grantedAuthorityListts = roles.stream()
                        .map(
                            role ->  new SimpleGrantedAuthority("ROLE_"+ role.toString().toUpperCase())
                        ).toList();

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                token.getSubject(),
                                null,
                                grantedAuthorityListts
                        );

                // Injetar o auth no SpringSecurity
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        }

        filterChain.doFilter(request, response);
    }
}
