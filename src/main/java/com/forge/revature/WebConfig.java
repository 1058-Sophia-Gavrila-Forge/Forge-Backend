package com.forge.revature;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//Cross-domain configuration class: configure cross-domain request
@Configuration //Declare the global configuration class
public class WebConfig implements WebMvcConfigurer {

    @Override
    public  void  addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(30*1000);
    }

}
