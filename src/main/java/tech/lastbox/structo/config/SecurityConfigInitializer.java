package tech.lastbox.structo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tech.lastbox.lastshield.security.SecurityConfig;
import tech.lastbox.lastshield.security.core.http.HttpMethod;

import java.util.List;


@Configuration
@ComponentScan(basePackages = "tech.lastbox.lastshield")
public class SecurityConfigInitializer {

    public SecurityConfigInitializer(SecurityConfig securityConfig) {
        securityConfig.corsAllowCredentials(true)
                .corsAllowedOrigins(List.of("*"))
                .setCsrfProtection(false)
                .corsAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"))
                .corsAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));

        securityConfig.addRouteAuthority("/login")
                .addRouteAuthority("/register")
                .addRouteAuthority("/user", "USER");

        securityConfig.build();
    }
}
