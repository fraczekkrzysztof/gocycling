package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.notification.NotificationDto;

import java.util.List;

public interface NotificationServiceV2 {
    List<NotificationDto> getUserNotifications(String userUid);

    void markNotificationAsRead(long notificationId);

    long getMaxNotificationIdForUser(String userUid);
}
