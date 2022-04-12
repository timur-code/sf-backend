package com.rant.sfbackend.controllers;

import com.rant.sfbackend.DTO.AuthenticationRequest;
import com.rant.sfbackend.DTO.AuthenticationResponse;
import com.rant.sfbackend.DTO.UserRequest;
import com.rant.sfbackend.DTO.UserResponse;
import com.rant.sfbackend.security.CustomUserDetailsService;
import com.rant.sfbackend.services.UserService;
import com.rant.sfbackend.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class AppController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    @Autowired
    public AppController(UserService userService, AuthenticationManager authenticationManager,
                         CustomUserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/")
    public ResponseEntity<?> getOwnInfo(HttpServletRequest request) {
        UserResponse userResponse = userService.getOwnInfo(request);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken (@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password", ex);
        }

        final UserDetails userDetails = userDetailsService.
                loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/authorize")
    public ResponseEntity<?> createAuthorization (@RequestBody UserRequest userRequest) throws Exception {
        try {
            userService.createUser(userRequest);
        } catch (InstanceAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        final UserDetails userDetails = userDetailsService.
                loadUserByUsername(userRequest.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
