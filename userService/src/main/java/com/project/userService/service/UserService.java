package com.project.userService.service;

import com.project.userService.external.repository.UserRepository;
import com.project.userService.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    UserRepository userRepository;
    public ResponseEntity<User> getUser(Integer id) {
       Optional<User> optionalUser = userRepository.findById(id);
       if(optionalUser.isPresent()){
           return ResponseEntity.ok(optionalUser.get());
       }
       return ResponseEntity.notFound().build();
    }

    public ResponseEntity<User> addUser(User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
}
