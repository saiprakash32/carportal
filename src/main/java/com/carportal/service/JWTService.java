package com.carportal.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {


    @Value("${jwt.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry-time}")
    private int expiry;

    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() throws Exception {
        algorithm=Algorithm.HMAC256(algorithmKey); // it has algorithm & secret key

 }
 public String generateToken(String username) {
    return JWT.create()
             .withClaim("name", username)
             .withExpiresAt(java.util.Date.from(java.time.Instant.now().plusSeconds(expiry)))
             .withIssuer(issuer)
             .sign(algorithm);

 }

    public String getUsername(String jwtToken) {
        DecodedJWT decodedJWT= JWT.require(algorithm)
                .withIssuer(issuer)
               .build()
               .verify(jwtToken);
            return   decodedJWT.getClaim("name").asString();
    }
}
