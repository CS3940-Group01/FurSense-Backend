package com.project.userService.application.controllers;

import com.project.userService.model.User;
import com.project.userService.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    UserService userService;


    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {

        return userService.getAllUsers();
    }
    @GetMapping("/getuser")
    public ResponseEntity<User> getUser(@RequestParam Integer id) {
        return userService.getUser(id);

    }

    @PostMapping("/adduser")
    public ResponseEntity<User> addUser(@RequestBody User user) {

        return userService.addUser(user);
    }

    @PostMapping("/editUser")
    public ResponseEntity<String> editUser(@RequestBody User user){
        return userService.editUser(user);
        }

    @PostMapping("/deleteUser")
    public ResponseEntity<String>deleteUser(@RequestParam Integer id){
        return userService.deleteUser(id);
    }
}
