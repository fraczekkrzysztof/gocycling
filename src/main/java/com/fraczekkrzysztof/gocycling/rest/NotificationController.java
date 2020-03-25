package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.entity.Notification;
import com.fraczekkrzysztof.gocycling.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/custom/notifications")
public class NotificationController {

    NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PutMapping("/markAsRead")
    public ResponseEntity<String> markNotificationAsRead(@RequestParam("notificationId") long notificationId) throws Exception {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
