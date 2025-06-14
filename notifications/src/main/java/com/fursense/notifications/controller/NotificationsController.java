package com.fursense.notifications.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    @PostMapping("/schedule")
    public ResponseEntity<?> schedulePush(@RequestBody Map<String, String> body) {
        String expoPushToken = body.get("expoPushToken");
        String scheduledAtStr = body.get("scheduledAt");

        if (expoPushToken == null || scheduledAtStr == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing fields"));
        }

        try {
            LocalDateTime scheduledTime = LocalDateTime.parse(scheduledAtStr);
            LocalDateTime now = LocalDateTime.now();

            long delayMillis = Duration.between(now, scheduledTime).toMillis();

            if (delayMillis <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Time must be in the future"));
            }

            System.out.println("✅ Notification scheduled at: " + scheduledAtStr);

            new Thread(() -> {
                try {
                    Thread.sleep(delayMillis);

                    Map<String, Object> pushMessage = new HashMap<>();
                    pushMessage.put("to", expoPushToken);
                    pushMessage.put("sound", "default");
                    pushMessage.put("title", "📅 Scheduled FurSense Alert");
                    pushMessage.put("body", "Your scheduled notification has arrived!");

                    RestTemplate restTemplate = new RestTemplate();
                    String expoApiUrl = "https://exp.host/--/api/v2/push/send";
                    ResponseEntity<String> response = restTemplate.postForEntity(expoApiUrl, pushMessage, String.class);

                    System.out.println("✅ Expo response: " + response.getBody());

                } catch (Exception e) {
                    System.err.println("❌ Error sending push: " + e.getMessage());
                }
            }).start();

            return ResponseEntity.ok(Map.of("status", "scheduled"));

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid date-time format"));
        }
    }
}
