package com.example.bookManagement.config;

import com.example.bookManagement.filter.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  // for spring security
@EnableMethodSecurity //for pre and post authorize
public class securityConfig {

    private static final String SCHEDULER_URL = "/api/scheduler/**";

    /*
     * Below is the only basic auth setup in which user details save in application.properties file
     * and all request is proceed and no pass/user save in database;
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, JWTAuthFilter jwtAuthFilter) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable) // Disable CSRF for APIs
                    .authorizeHttpRequests(auth ->
                                                   auth.requestMatchers("/api/auth/getAuthenticate")
                                                       .permitAll()
                                                       .requestMatchers("/api/users/**")
                                                       .permitAll()
//                                                       .requestMatchers("/api/scheduler/getAllCronScheduler")
//                                                       .hasRole(Role.USER.name())
//                                                       .requestMatchers(HttpMethod.GET, SCHEDULER_URL)
//                                                       .hasAuthority(Permission.USER_READ.name())
//                                                       .requestMatchers(HttpMethod.POST, SCHEDULER_URL)
//                                                       .hasAuthority(Permission.USER_WRITE.name())
//                                                       .requestMatchers(HttpMethod.PUT, SCHEDULER_URL)
//                                                       .hasAuthority(Permission.USER_WRITE.name())
                                                       .requestMatchers("api/book/getAllBooks")
                                                       .permitAll()

                                                       // All other endpoints must be authenticated
                                                       .anyRequest()
                                                       .authenticated())
                    //Enables Basic Authentication
                    .httpBasic(Customizer.withDefaults())
                    // OAuth2 Login support
                    .oauth2Login(Customizer.withDefaults());
        // Inserts your JWT filter before Basic Auth filter
        httpSecurity.addFilterBefore(jwtAuthFilter,
                                     UsernamePasswordAuthenticationFilter.class
        );
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
