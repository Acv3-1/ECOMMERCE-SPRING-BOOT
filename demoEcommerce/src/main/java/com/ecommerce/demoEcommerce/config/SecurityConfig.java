package com.ecommerce.demoEcommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() // Permitir acceso a todas las rutas sin autenticación
            )
            .csrf().disable() // Deshabilitar CSRF si no es necesario
            .formLogin().disable() // Deshabilitar el formulario de inicio de sesión predeterminado
            .httpBasic().disable(); // Deshabilitar la autenticación básica

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}