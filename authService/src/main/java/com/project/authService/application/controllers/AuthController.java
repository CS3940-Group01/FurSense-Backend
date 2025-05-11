package com.project.authService.application.controllers;

import com.project.authService.dto.AuthRequest;
import com.project.authService.model.User;
import com.project.authService.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;


//    @GetMapping("/getAllUsers")
//    public ResponseEntity<List<User>> getAllUsers() {
//
//        return authService.getAllUsers();
//    }
//    @GetMapping("/getuser")
//    public ResponseEntity<User> getUser(@RequestParam Integer id) {
//        return authService.getUser(id);
//
//    }

//    @PostMapping("/editUser")
//    public ResponseEntity<String> editUser(@RequestBody User user){
//        return authService.editUser(user);
//        }
//
//    @PostMapping("/deleteUser")
//    public ResponseEntity<String>deleteUser(@RequestParam Integer id){
//        return authService.deleteUser(id);
//    }


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
    public String validateToken(@RequestParam String token) {
        authService.validateToken(token);
        return "valid token";
    }
}
