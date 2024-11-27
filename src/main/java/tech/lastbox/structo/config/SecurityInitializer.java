package tech.lastbox.structo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tech.lastbox.jwt.JwtConfig;
import tech.lastbox.jwt.JwtService;
import tech.lastbox.lastshield.security.SecurityConfig;


@Configuration
@ComponentScan(basePackages = "tech.lastbox.lastshield")
public class SecurityInitializer {

    private final JwtConfig jwtConfig;

    public SecurityInitializer (SecurityConfig securityConfig, JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        securityConfig.corsAllowCredentials(true)
                      .setCsrfProtection(false)
                      .build();
    }

    @Bean
    public JwtService jwtService() {
        return new JwtService(jwtConfig);
    }
}
