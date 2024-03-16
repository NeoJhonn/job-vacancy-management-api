package br.com.jhonnyazevedo.job_vacancy_management.security;

import br.com.jhonnyazevedo.job_vacancy_management.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
            throws ServletException, IOException {
        //Zerando o contexto do usuário
        SecurityContextHolder.getContext().setAuthentication(null);

        // pega o token e armazena em header
        String header = request.getHeader("Authorization");

        // verificar se esta pegando o token do header
        System.out.println("Pegou o header = "+header);

        if (header != null) {
            var subjectToken = this.jwtProvider.validateToken(header);

            if (subjectToken.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // passa o id da company para ser salva quando for criado um novo Job
            request.setAttribute("company_id", subjectToken);
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            subjectToken,
                            null,
                            Collections.emptyList()
                    );

            // Injetar o auth no SpringSecurity
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // passa o filtro pro controller
        filterChain.doFilter(request, response);

    }
}
