package br.com.jhonnyazevedo.job_vacancy_management.security;

import br.com.jhonnyazevedo.job_vacancy_management.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component// o filter vai ser um component pro Spring consegui gerenciar o clico de vida dele
public class SecurityCandidateFilter extends OncePerRequestFilter {

    @Autowired
    private JWTCandidateProvider jwtCandidateProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Zerar autenticações anteriores
        SecurityContextHolder.getContext().setAuthentication(null);

        String header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/candidate")) {

            if (header != null) {
                var token = this.jwtCandidateProvider.validateToken(header);

                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                request.setAttribute("candidate_id", token.getSubject());

                var roles = token.getClaim("role");

            }
        }

        filterChain.doFilter(request, response);
    }
}
