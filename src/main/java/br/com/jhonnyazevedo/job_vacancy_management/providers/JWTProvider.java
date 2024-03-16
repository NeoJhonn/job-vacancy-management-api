package br.com.jhonnyazevedo.job_vacancy_management.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {

    @Value("${security.token.secrete}")
    private String secretKey;

    public String validateToken(String token) {
        // Tirar o "Bearer " pois só vamos usar o token em si
        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

       try {
           //pegar o id da Company que esta autenticada
           var subject = JWT.require(algorithm)
                   .build()
                   .verify(token)
                   .getSubject(); //pegar o id da Company que esta autenticada

           return subject;

       } catch (JWTVerificationException e) {
           e.printStackTrace();
           return "";
       }
    }
}
