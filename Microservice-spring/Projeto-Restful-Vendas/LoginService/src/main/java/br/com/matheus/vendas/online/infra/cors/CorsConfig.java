package br.com.matheus.vendas.online.infra.cors;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@SuppressWarnings("null")

public class CorsConfig implements WebMvcConfigurer{

    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("http://localhost:8084")
        .allowedMethods("GET","POST");        
        
    }

    

}
