package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.dto.notification.NotificationDto;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import com.fraczekkrzysztof.gocycling.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceV2Impl implements NotificationServiceV2 {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public List<NotificationDto> getUserNotifications(String userUid) {
        List<Notification> notificationList = notificationRepository.findByUserUid(userUid,
                PageRequest.of(0, 1000, Sort.by(Sort.Direction.DESC, "created")));
        return notificationMapper.mapNotificationListToNotificationDtoList(notificationList);
    }

    @Override
    public void markNotificationAsRead(long notificationId) {
        Notification notificationToMarkAsRead = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no notification of id %d", notificationId)));
        notificationToMarkAsRead.setRead(true);
        notificationRepository.save(notificationToMarkAsRead);
    }

    @Override
    public long getMaxNotificationIdForUser(String userUid) {
        List<Notification> notificationList = notificationRepository.findByUserUid(userUid, Pageable.unpaged());
        return notificationList.stream()
                .max(Comparator.comparingLong(Notification::getId))
                .map(Notification::getId)
                .orElse(-1L);

    }
}
