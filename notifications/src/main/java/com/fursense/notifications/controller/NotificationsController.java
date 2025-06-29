package com.fursense.notifications.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    @GetMapping("/ready")
    public ResponseEntity<?> ready(@RequestHeader("userid") Integer userId) {
        System.out.println(userId);
        return ResponseEntity.ok().build();
    }
}
