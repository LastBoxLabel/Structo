package tech.lastbox.structo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tech.lastbox.jwt.ExpirationTimeUnit;
import tech.lastbox.jwt.JwtAlgorithm;
import tech.lastbox.jwt.JwtConfig;
import tech.lastbox.jwt.JwtService;
import tech.lastbox.lastshield.security.SecurityConfig;
import tech.lastbox.structo.config.enviroment.JwtProperties;
import tech.lastbox.structo.repositories.TokenRepository;


import java.util.List;


@Configuration
@ComponentScan(basePackages = "tech.lastbox.lastshield")
public class SecurityConfigInitializer {

    public SecurityConfigInitializer(SecurityConfig securityConfig) {
        securityConfig.corsAllowCredentials(true)
                .corsAllowedOrigins(List.of("*"))
                .corsAllowedMethods(List.of("*"))
                .addRouteAuthority("/login")
                .addRouteAuthority("/register")
                .addRouteAuthority("/api-docs/**")
                .addRouteAuthority("/swagger-ui/**")
                .addRouteAuthority("/admin", "ADMIN")
                .addRouteAuthority("/actuator", "ADMIN")
                .addRouteAuthority("/actuator/**", "ADMIN")
                .addRouteAuthority("/**", List.of("USER", "ADMIN"))
                .setCsrfProtection(false);
        securityConfig.build();
    }

    /*@Bean
    public SecurityConfig initializeSecurity(CoreSecurityConfig coreSecurityConfig, CorsConfig corsConfig) {
        SecurityConfig securityConfig = new SecurityConfig(coreSecurityConfig, corsConfig);

        return securityConfig;
    }*/





    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
