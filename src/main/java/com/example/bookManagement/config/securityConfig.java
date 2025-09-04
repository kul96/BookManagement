package com.example.bookManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfig {

    /*
     * Below is the only basic auth setup in which user details save in application.properties file
     * and all request is proceed and no pass/user save in database;
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest()
                                                       .authenticated())
                    .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
