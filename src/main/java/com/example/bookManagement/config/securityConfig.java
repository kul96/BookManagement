package com.example.bookManagement.config;

import com.example.bookManagement.filter.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class securityConfig {

    /*
     * Below is the only basic auth setup in which user details save in application.properties file
     * and all request is proceed and no pass/user save in database;
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, JWTAuthFilter jwtAuthFilter) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth ->
                                                   auth.requestMatchers("/h2-console/**", "/api/auth/getAuthenticate")
                                                       .permitAll()
                                                       .anyRequest()
                                                       .authenticated())
                    .httpBasic(Customizer.withDefaults()); //Enables Basic Authentication
        httpSecurity.addFilterBefore(jwtAuthFilter,
                                     UsernamePasswordAuthenticationFilter.class
        ); // Inserts your JWT filter before Basic Auth filter
        return httpSecurity.build();
        // from above setup both flow of filter (jwt + basic auth) run
    }

//    @Bean // bean already created
//    public UserDetailsService customUserDetailsService() {
////        create custom_user_details_service object and inject in authentication manager method
//        return new CustomUserDetailsService();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//      create password encoder object and inject in authentication manager method
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);

    }

}
