package com.project.userService.application.controllers;

import com.project.userService.model.User;
import com.project.userService.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    UserService userService;
    @GetMapping("/getuser")
    public ResponseEntity<User> getUser(@RequestParam Integer id) {
        return userService.getUser(id);

    }

    @PostMapping("/adduser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
