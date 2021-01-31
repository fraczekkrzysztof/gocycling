package com.fraczekkrzysztof.gocycling.rest;


import com.fraczekkrzysztof.gocycling.dto.notification.NotificationListResponseDto;
import com.fraczekkrzysztof.gocycling.service.NotificationServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/user/{userUid}")
public class NotificationControllerV2 {

    private final NotificationServiceV2 notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<NotificationListResponseDto> getUserNotification(@PathVariable("userUid") String userUid, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationService.getUserNotifications(userUid, pageable));
    }

    @GetMapping("/notifications/max")
    public ResponseEntity<Long> getMaxNotificationIdForUser(@PathVariable("userUid") String userUid) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationService.getMaxNotificationIdForUser(userUid));
    }

    @PatchMapping("/notifications/{id}/markAsRead")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable("id") long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("");
    }
}
