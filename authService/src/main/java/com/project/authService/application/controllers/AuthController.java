package com.project.authService.application.controllers;

import com.project.authService.dto.AuthRequest;
import com.project.authService.model.User;
import com.project.authService.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        System.out.println(user);
        return authService.addUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        return authService.validate(token);
    }
}
