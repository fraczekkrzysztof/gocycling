package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

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

    @GetMapping("/findMaxNotificationIdForUser")
    public ResponseEntity<Long> findMaxNotificationIdForUser(@RequestParam("userUid") String userUid){
        try{
            long notId = notificationService.getMaxNotificationIdForUser(userUid);
            return ResponseEntity.status(HttpStatus.OK).body(notId);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.OK).body(-1l);
        }

    }
}
