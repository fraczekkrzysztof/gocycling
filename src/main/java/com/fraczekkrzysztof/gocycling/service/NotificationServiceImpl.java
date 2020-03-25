package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import com.fraczekkrzysztof.gocycling.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void markNotificationAsRead(long notificationId) throws Exception {
        Notification toUpdate = notificationRepository.findById(notificationId).orElseThrow(() -> new Exception("There is no notification id "+ notificationId));
        toUpdate.setRead(true);
        notificationRepository.save(toUpdate);
    }
}
