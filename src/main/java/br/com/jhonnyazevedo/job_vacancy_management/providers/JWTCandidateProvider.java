package br.com.jhonnyazevedo.job_vacancy_management.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTCandidateProvider {

    @Value("${security.token.secrete}")
    private String secretKey;

    public DecodedJWT validateToken(String token) {
        // Tirar o "Bearer " pois só vamos usar o token em si
        token = token.replace("Bearer ", "");

        // criar um algorítmo
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            var tokenDecoded = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return  tokenDecoded;

        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
