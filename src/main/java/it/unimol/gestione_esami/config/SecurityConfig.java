package it.unimol.gestione_esami.config;

import it.unimol.gestione_esami.security.JwtRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/api-docs/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/exams").hasAnyAuthority("ROLE_ADMINISTRATIVE", "ROLE_PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/exams/**").hasAuthority("ROLE_ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/exams/**").hasAuthority("ROLE_ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/exams/calendar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/exams/available").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/enrollments/*/enroll").hasAuthority("ROLE_STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/v1/exams/*/grades").hasAuthority("ROLE_PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/grades/student/**").hasAnyAuthority("ROLE_ADMINISTRATIVE", "ROLE_PROFESSOR")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new JwtRoleConverter());
        return converter;
    }
}