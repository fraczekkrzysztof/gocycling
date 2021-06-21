package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.dto.notification.NotificationDto;
import com.fraczekkrzysztof.gocycling.dto.notification.NotificationListResponseDto;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import com.fraczekkrzysztof.gocycling.mapper.NotificationMapper;
import com.fraczekkrzysztof.gocycling.paging.PageDto;
import com.fraczekkrzysztof.gocycling.paging.PagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PagingService pagingService;

    @Override
    public NotificationListResponseDto getUserNotifications(String userUid, Pageable pageable) {
        Page<Notification> pagedNotification = notificationRepository.findByUserUid(userUid, pageable);
        PageDto pageDto = pagingService.generatePageInfo(pagedNotification);
        List<NotificationDto> mappedNotifications = notificationMapper.mapNotificationListToNotificationDtoList(pagedNotification.getContent());
        return NotificationListResponseDto.builder().notifications(mappedNotifications).page(pageDto).build();
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
        Page<Notification> notificationList = notificationRepository.findByUserUid(userUid, Pageable.unpaged());
        return notificationList.getContent().stream()
                .max(Comparator.comparingLong(Notification::getId))
                .map(Notification::getId)
                .orElse(-1L);

    }
}
