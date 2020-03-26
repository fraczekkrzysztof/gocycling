package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import com.fraczekkrzysztof.gocycling.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Override
    public long getMaxNotificationIdForUser(String userUid) {
        List<Notification> notificationList = notificationRepository.findByUserUid(userUid, Pageable.unpaged()).getContent();
        return notificationList.stream().mapToLong(Notification::getId).max().orElseThrow(NoSuchElementException::new);
    }
}
