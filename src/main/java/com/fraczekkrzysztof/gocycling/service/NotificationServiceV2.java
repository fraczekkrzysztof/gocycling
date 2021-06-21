package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.notification.NotificationListResponseDto;
import org.springframework.data.domain.Pageable;

public interface NotificationServiceV2 {
    NotificationListResponseDto getUserNotifications(String userUid, Pageable pageable);

    void markNotificationAsRead(long notificationId);

    long getMaxNotificationIdForUser(String userUid);
}
