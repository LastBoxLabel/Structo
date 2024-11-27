package tech.lastbox.structo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Component;
import tech.lastbox.jwt.ExpirationTimeUnit;
import tech.lastbox.jwt.JwtAlgorithm;
import tech.lastbox.jwt.JwtConfig;
import tech.lastbox.structo.repositories.TokenRepository;

@Component
public class JwtConfiguration {
    private final TokenRepository tokenRepository;
    private final String secretKey;
    private final String issuer;

    public JwtConfiguration(@Value("${jwt.secretkey}") String secretKey, @Value("${jwt.issuer}") String issuer, TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
        this.secretKey = secretKey;
        this.issuer = issuer;
    }

    @Bean
    public JwtConfig getJwtConfig() {
        return new JwtConfig(
                JwtAlgorithm.HMAC256,
                secretKey,
                issuer,
                3,
                ExpirationTimeUnit.DAYS,
                tokenRepository
        );
    }
}
