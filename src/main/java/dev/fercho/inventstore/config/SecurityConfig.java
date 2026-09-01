package dev.fercho.inventstore.config;

import dev.fercho.inventstore.security.CustomAccessDeniedHandler;
import dev.fercho.inventstore.security.CustomAuthenticationEntryPoint;
import dev.fercho.inventstore.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("Authorization", "Content-Type")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomAuthenticationEntryPoint authenticationEntryPoint,
            CustomAccessDeniedHandler accessDeniedHandler) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors-> {})
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()


                        .requestMatchers("/api/reportes/**")
                        .hasRole("ADMINISTRADOR")

                        .requestMatchers("/api/usuarios/**")
                        .hasRole("ADMINISTRADOR")

                        .requestMatchers(HttpMethod.POST, "/api/cajas/**")
                        .hasAnyRole("ADMINISTRADOR", "CAJERO","COCINA")

                        .requestMatchers(
                                "/api/compras/**",
                                "/api/proveedores/**",
                                "/api/inventario/**"
                        ).hasAnyRole("ADMINISTRADOR", "COCINA")

                        .requestMatchers(HttpMethod.GET, "/api/insumos/**")
                        .hasAnyRole("ADMINISTRADOR", "COCINA", "CAJERO")

                        .requestMatchers("/api/insumos/**")
                        .hasAnyRole("ADMINISTRADOR", "COCINA")

                        // PRODUCTOS
                        .requestMatchers(HttpMethod.GET, "/api/productos/**")
                        .hasAnyRole("ADMINISTRADOR", "COCINA", "CAJERO")

                        .requestMatchers("/api/productos/**")
                        .hasAnyRole("ADMINISTRADOR", "COCINA")

                        // CATEGORÍAS
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**")
                        .hasAnyRole("ADMINISTRADOR", "COCINA", "CAJERO")

                        .requestMatchers("/api/categorias/**")
                        .hasAnyRole("ADMINISTRADOR", "COCINA")

                        // ÓRDENES
                        .requestMatchers("/api/ordenes/**")
                        .hasAnyRole("ADMINISTRADOR", "COCINA", "CAJERO")

                        // PAGOS
                        .requestMatchers("/api/pagos/**")
                        .hasAnyRole("ADMINISTRADOR", "COCINA", "CAJERO")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}