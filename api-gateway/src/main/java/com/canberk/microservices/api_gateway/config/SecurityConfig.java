package com.canberk.microservices.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final String[] noAuthUrls = {"swagger-ui.html", "/swagger-ui/**" ,"/swagger-resources/**",
            "/v3/api-docs/**","/webjars/**","/api-docs/**","/aggregate/**"};
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return  http.authorizeHttpRequests(authorizeRequests
                -> authorizeRequests
                      .requestMatchers(noAuthUrls)
                      .permitAll()
                      .anyRequest().authenticated())
                .oauth2ResourceServer
                        (oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                 .build();
    }
}
