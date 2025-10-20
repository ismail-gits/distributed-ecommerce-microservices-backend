package com.ecommerce.microservices.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

  private final String[] freeResourceUrls = {
      "/swagger-ui.html",
      "/swagger-ui/**",
      "/v3/api-docs/**",
      "/swagger-resources/**",
      "/api-docs/**",
      "/aggregate/**"
  };

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
    return httpSecurity
        .csrf(csrfConfig -> csrfConfig.disable())
        .authorizeExchange(auth -> auth
            .pathMatchers(freeResourceUrls).permitAll()
            .anyExchange().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .build();
  }
}
