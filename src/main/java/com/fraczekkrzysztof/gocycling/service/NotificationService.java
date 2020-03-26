package com.fraczekkrzysztof.gocycling.service;

public interface NotificationService {

    void markNotificationAsRead(long notificationId) throws Exception;
    long getMaxNotificationIdForUser(String userUid);
}
