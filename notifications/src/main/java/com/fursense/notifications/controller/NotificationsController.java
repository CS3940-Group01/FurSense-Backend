package com.fursense.notifications.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    @GetMapping("/ready")
    public ResponseEntity<?> ready() {
        return ResponseEntity.ok().build();
    }
}
