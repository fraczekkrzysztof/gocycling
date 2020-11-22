package com.fraczekkrzysztof.gocycling.dto.notification;

import com.fraczekkrzysztof.gocycling.entity.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationDto {

    private long id;
    private String userUid;
    private String title;
    private String content;
    private LocalDateTime created;
    private boolean read = false;
    private NotificationType type;
    private long clubId;
    private long eventId;
}
