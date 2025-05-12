package com.project.userService.application.controllers;

import com.project.userService.model.User;
import com.project.userService.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getuser")
    public ResponseEntity<User> getUser(@RequestHeader("userId") String userIdHeader) {
        try {
            Integer userId = Integer.parseInt(userIdHeader);
            return userService.getUser(userId);
        } catch (NumberFormatException e) {
            log.error("Invalid userId in header: {}", userIdHeader, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/editUser")
    public ResponseEntity<String> editUser(@RequestHeader("userId") String userIdHeader,
                                           @RequestBody User user) {
        try {
            Integer userId = Integer.parseInt(userIdHeader);
            user.setId(userId);
            return userService.editUser(user);
        } catch (NumberFormatException e) {
            log.error("Invalid userId in header: {}", userIdHeader, e);
            return ResponseEntity.badRequest().body("Invalid userId format.");
        }
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestHeader("userId") String userIdHeader) {
        try {
            Integer userId = Integer.parseInt(userIdHeader);
            return userService.deleteUser(userId);
        } catch (NumberFormatException e) {
            log.error("Invalid userId in header: {}", userIdHeader, e);
            return ResponseEntity.badRequest().body("Invalid userId format.");
        }
    }
}