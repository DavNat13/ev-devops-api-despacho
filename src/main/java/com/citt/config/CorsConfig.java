package com.citt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Esta clase configura las políticas de CORS (Cross-Origin Resource Sharing) para la aplicación. Permite que la API REST sea accesible desde cualquier origen, lo que es útil durante el desarrollo y pruebas. Sin embargo, en un entorno de producción, se recomienda restringir los orígenes permitidos para mejorar la seguridad.
@Configuration
public class CorsConfig {
    @Bean
        public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica a todos los endpoints
                        .allowedOrigins("*") // Permite cualquier origen
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("*") // Permite cualquier cabecera
                        .allowCredentials(false); // Deshabilita credenciales compartidas (true si necesitas cookies o autenticación)
            }
        };
    }

}
