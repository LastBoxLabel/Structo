package tech.lastbox.structo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tech.lastbox.lastshield.security.SecurityConfig;

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
                .addRouteAuthority("/user", "USER")
                .setCsrfProtection(false);
        securityConfig.build();
    }
}
