package br.com.jhonnyazevedo.job_vacancy_management.services;

import br.com.jhonnyazevedo.job_vacancy_management.dtos.AuthCompanyDTO;
import br.com.jhonnyazevedo.job_vacancy_management.dtos.AuthCompanyResponseDTO;
import br.com.jhonnyazevedo.job_vacancy_management.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyService {

    // Peguei o valor do application.propeties e setei na variável secretKey
    // Senha mestre para meu tokens
    @Value("${security.token.secrete}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        // Verificar se a Company existe, se não existir lançar uma exceção
        var company = companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> {
                   throw new UsernameNotFoundException("Username/password incorrect");
                }
        );

        // Caso exista a Company
        // Verificar se a senha esta correta
        //                                           (senha passada pelo Front, senha do banco com criptografia)
        var passwordMatches = this.passwordEncoder
                .matches(authCompanyDTO.getPassword(), company.getPassword());

        // Se não estiver correta -> Erro
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        // Se estiver correta -> Gerar o token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        // tempo de expiração do token
        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("Javagas")
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))// tempo de duração do token, no caso 2 horas(Duration.ofHours(2)
            .withSubject(company.getId().toString())//passa o id da Company e converte pra String
            .withExpiresAt(expiresIn)// tempo de expiração do token, passa o expiresIn que criei antes como parâmetro
            .withClaim("role", Arrays.asList("COMPANY"))// indica qual a role
            .sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return authCompanyResponseDTO;
    }

}
