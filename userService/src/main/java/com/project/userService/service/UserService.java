package com.project.userService.service;

import com.project.userService.external.repository.UserRepository;
import com.project.userService.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    public ResponseEntity<User> getUser(Integer id) {
       Optional<User> optionalUser = userRepository.findById(id);
       if(optionalUser.isPresent()){
           return ResponseEntity.ok(optionalUser.get());
       }
       return ResponseEntity.notFound().build();
    }

    public ResponseEntity<User> addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    public ResponseEntity<String> editUser(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if(optionalUser.isPresent()){
            userRepository.save(user);
            return ResponseEntity.ok("User updated successfully");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> deleteUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            userRepository.delete(optionalUser.get());
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
