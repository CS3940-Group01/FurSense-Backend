package com.project.userService.service;

import com.project.userService.dto.UserDto;
import com.project.userService.external.repository.UserRepository;
import com.project.userService.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    UserRepository userRepository;

    public ResponseEntity<UserDto> getUser(Integer id) {
       Optional<User> optionalUser = userRepository.findById(id);
       if(optionalUser.isPresent()){
           User user = optionalUser.get();
           UserDto userDto = new UserDto();
           userDto.setId(user.getId());
           userDto.setUsername(user.getUsername());
           userDto.setName(user.getName());
           userDto.setEmail(user.getEmail());
           userDto.setPhoneNumber(user.getPhoneNumber());
           userDto.setAddress(user.getAddress());
           userDto.setProfilePhoto(user.getProfilePhoto());
           return ResponseEntity.ok(userDto);
       }
       return ResponseEntity.notFound().build();
    }

    public ResponseEntity<User> addUser(User user) {
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
