package br.com.jhonnyazevedo.job_vacancy_management.security;

import br.com.jhonnyazevedo.job_vacancy_management.providers.JWTCompanyProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityCompanyFilter extends OncePerRequestFilter {

    @Autowired
    private JWTCompanyProvider jwtCompanyProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
            throws ServletException, IOException {
        //Zerando o contexto do usuário
        //SecurityContextHolder.getContext().setAuthentication(null);

        // pega o token e armazena em header
        String header = request.getHeader("Authorization");

        // verificar se esta pegando o token do header
        System.out.println("Pegou o header = "+header);

        if (request.getRequestURI().startsWith("/company")) {

            if (header != null) {
                var token = this.jwtCompanyProvider.validateToken(header);

                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                // fazer mapeamento das roles
                var roles = token.getClaim("role").asList(Object.class);
                // Sempre usar o prefixo "ROLE_" é padrão do Spring Security
                var grantedAuthorityList = roles.stream().map(role -> new SimpleGrantedAuthority(
                        "ROLE_"+role.toString().toUpperCase())).toList();

                // passa o id da company para ser salva quando for criado um novo Job
                request.setAttribute("company_id", token.getSubject());
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                token.getSubject(),
                                null,
                                grantedAuthorityList
                        );

                // Injetar o auth no SpringSecurity
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // passa o filtro pro controller
        filterChain.doFilter(request, response);

    }
}
