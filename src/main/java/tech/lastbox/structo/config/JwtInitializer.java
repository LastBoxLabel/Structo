package tech.lastbox.structo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.lastbox.jwt.ExpirationTimeUnit;
import tech.lastbox.jwt.JwtAlgorithm;
import tech.lastbox.jwt.JwtConfig;
import tech.lastbox.jwt.JwtService;
import tech.lastbox.structo.config.enviroment.JwtProperties;
import tech.lastbox.structo.repositories.TokenRepository;

@Configuration
public class JwtInitializer {

    private final JwtProperties jwtProperties;
    private final TokenRepository tokenRepository;

    public JwtInitializer(JwtProperties jwtProperties, TokenRepository tokenRepository) {
        this.jwtProperties = jwtProperties;
        this.tokenRepository = tokenRepository;
        System.out.println(jwtProperties.getIssuer());
    }

    private JwtConfig getJwtConfig() {
        return new JwtConfig(JwtAlgorithm.HMAC256,
                jwtProperties.getSecretKey(),
                jwtProperties.getIssuer(),
                7,
                ExpirationTimeUnit.DAYS,
                tokenRepository);
    }

    @Bean
    public JwtService jwtService() {
        return new JwtService(getJwtConfig());
    }
}
