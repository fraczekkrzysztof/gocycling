package com.fraczekkrzysztof.gocycling.mapper;

import com.fraczekkrzysztof.gocycling.dto.notification.NotificationDto;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationMapper {

    public List<NotificationDto> mapNotificationListToNotificationDtoList(List<Notification> notificationList) {
        return notificationList.stream()
                .map(this::mapNotificationToNotificationDto)
                .collect(Collectors.toList());
    }

    private NotificationDto mapNotificationToNotificationDto(Notification n) {
        return NotificationDto.builder()
                .id(n.getId())
                .userUid(n.getUserUid())
                .title(n.getTitle())
                .content(n.getContent())
                .type(n.getType())
                .clubId(n.getClubId())
                .eventId(n.getEventId())
                .created(n.getCreated())
                .read(n.isRead())
                .build();
    }
}
