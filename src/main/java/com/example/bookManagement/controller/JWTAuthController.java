package com.example.bookManagement.controller;

import com.example.bookManagement.entity.JWTAuth;
import com.example.bookManagement.util.JWTAuthTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class JWTAuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTAuthTokenUtils jwtAuthUtils;

    @Autowired
    public JWTAuthController(AuthenticationManager authenticationManager, JWTAuthTokenUtils jwtAuthUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtAuthUtils = jwtAuthUtils;
    }

    @PostMapping("/getAuthenticate")
    public String getToken(@RequestBody JWTAuth auth) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtAuthUtils.generateJWTToken(authentication.getName()); // token generated
        } else {
            return "unauthorized";
        }
    }
}
