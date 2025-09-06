package com.example.bookManagement.filter;

import com.example.bookManagement.service.CustomUserDetailsService;
import com.example.bookManagement.util.JWTAuthTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTAuthTokenUtils jwtAuthTokenUtils;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JWTAuthFilter(JWTAuthTokenUtils jwtAuthTokenUtils, CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthTokenUtils = jwtAuthTokenUtils;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);// Bearer <token>
            String username = jwtAuthTokenUtils.extractJWTTokenUserName(token); // extract username from token(unique)

            if (username != null && SecurityContextHolder.getContext()
                                                         .getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(
                        username); // fetch username from db
                if (jwtAuthTokenUtils.validateToken(username, userDetails,
                                                    token
                )) { // check expiry and username from token
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request)); // set request details to token for more information
                    SecurityContextHolder.getContext()
                                         .setAuthentication(usernamePasswordAuthenticationToken);
                }

            }
        }
        filterChain.doFilter(request, response); // delegate to next filter
    }
}
