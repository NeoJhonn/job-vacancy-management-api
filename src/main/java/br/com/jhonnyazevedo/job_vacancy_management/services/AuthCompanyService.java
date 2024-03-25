package br.com.jhonnyazevedo.job_vacancy_management.services;

import br.com.jhonnyazevedo.job_vacancy_management.dtos.AuthCompanyDTO;
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

    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
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
        var token = JWT.create().withIssuer("Javagas")
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))// tempo de duração do token, no caso 2 horas(Duration.ofHours(2)
            .withSubject(company.getId().toString())//passa o id da Company e converte pra String
            .sign(algorithm);



        return token;
    }

}
