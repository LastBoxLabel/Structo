package tech.lastbox.structo.config;

import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Component;
import tech.lastbox.jwt.ExpirationTimeUnit;
import tech.lastbox.jwt.JwtAlgorithm;
import tech.lastbox.jwt.JwtConfig;
import tech.lastbox.jwt.JwtService;

@Component
public class JwtInitializer {
    private final JwtConfig jwtConfig = new JwtConfig(JwtAlgorithm.HMAC256,
            "pindamonhangaba", "isso", 2L, ExpirationTimeUnit.DAYS);

    @Bean
    public JwtService initialize(){
        return new JwtService(jwtConfig);
    }

}
