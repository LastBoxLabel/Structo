package tech.lastbox.structo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tech.lastbox.lastshield.security.SecurityConfig;


@Configuration
@ComponentScan(basePackages = "tech.lastbox.lastshield")
public class SecurityInitializer {

    public SecurityInitializer (SecurityConfig securityConfig) {
        securityConfig.corsAllowCredentials(true)
                      .setCsrfProtection(false)
                      .build();

    }
}
