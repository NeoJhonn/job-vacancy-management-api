package br.com.jhonnyazevedo.job_vacancy_management.services;

import br.com.jhonnyazevedo.job_vacancy_management.dtos.AuthCandidateRequestDTO;
import br.com.jhonnyazevedo.job_vacancy_management.dtos.AuthCandidateResponseDTO;
import br.com.jhonnyazevedo.job_vacancy_management.repositories.CandidateRepository;
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
public class AuthCandidateService {

    // Peguei o valor do application.propeties e setei na variável secretKey
    // Senha mestre para meus tokens
    @Value("${security.token.secrete.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username/password incorrect");
                });

        // Verificar se a senha passa é igual senha do banco de dados
        var passwordMatches = this.passwordEncoder.
         matches(authCandidateRequestDTO.password(), candidate.getPassword());

        // se não for igual lançar excessão
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        // Se estiver correta -> Gerar o token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
                .withIssuer("Javagas")
                .withExpiresAt(expiresIn)// tempo de duração do token, no caso 10min(Duration.ofMinutes(10))
                .withClaim("role", Arrays.asList("candidate"))// role do usuário, no caso um Candidate
                .withSubject(candidate.getId().toString())//passa o id do Candidate e converte pra String
                .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO
                .builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return authCandidateResponse;
    }
}
