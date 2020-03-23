package com.fraczekkrzysztof.gocycling.service.notification;

public interface NotificationGenerator {

    void addEventIdToUpdate(long id);
    void addEventIdToCancel(long id);
    void generateNotification();
}
