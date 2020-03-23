package com.fraczekkrzysztof.gocycling.service.notification;

public interface NotificationGenerator {

    void addEventId(long id);
    void generateNotification();
}
