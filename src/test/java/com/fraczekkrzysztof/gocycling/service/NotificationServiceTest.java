package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.dto.notification.NotificationDto;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import com.fraczekkrzysztof.gocycling.entity.NotificationType;
import com.fraczekkrzysztof.gocycling.mapper.NotificationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class NotificationServiceTest {

    @MockBean
    NotificationRepository notificationRepository;
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    NotificationServiceV2 notificationServiceV2;

    List<Notification> fakeUserNotificationList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Notification fakeNotification1 = Notification.builder()
                .userUid("1234")
                .clubId(1L)
                .eventId(1L)
                .content("Someone created event in your club")
                .created(LocalDateTime.of(2021, 01, 19, 18, 00, 00, 00))
                .read(false)
                .title("New event")
                .type(NotificationType.EVENT)
                .id(999L)
                .build();
        Notification fakeNotification2 = Notification.builder()
                .userUid("1234")
                .clubId(2L)
                .eventId(2L)
                .content("New message")
                .created(LocalDateTime.of(2021, 01, 19, 18, 02, 00, 00))
                .read(false)
                .title("Someone left a message in event you confirmed")
                .type(NotificationType.CONVERSATION)
                .id(100500L)
                .build();
        fakeUserNotificationList.addAll(Arrays.asList(fakeNotification1, fakeNotification2));
    }

    @Test
    void shouldReturnAllUserNotification() {
        NotificationDto expectedNotificationDto1 = NotificationDto.builder()
                .userUid("1234")
                .clubId(1L)
                .eventId(1L)
                .content("Someone created event in your club")
                .created(LocalDateTime.of(2021, 01, 19, 18, 00, 00, 00))
                .read(false)
                .title("New event")
                .type(NotificationType.EVENT)
                .id(999L)
                .build();
        NotificationDto expectedNotificationDto2 = NotificationDto.builder()
                .userUid("1234")
                .clubId(2L)
                .eventId(2L)
                .content("New message")
                .created(LocalDateTime.of(2021, 01, 19, 18, 02, 00, 00))
                .read(false)
                .title("Someone left a message in event you confirmed")
                .type(NotificationType.CONVERSATION)
                .id(100500L)
                .build();
        List<Notification> sortedNotificationList = fakeUserNotificationList.stream().sorted(Comparator.comparing(Notification::getCreated).reversed()).collect(Collectors.toList());
        when(notificationRepository.findByUserUid("1234", PageRequest.of(0, 1000, Sort.by(Sort.Direction.DESC, "created"))))
                .thenReturn(sortedNotificationList);

        //when
        List<NotificationDto> returnedNotificationList = notificationServiceV2.getUserNotifications("1234");

        //then
        assertThat(returnedNotificationList).containsExactly(expectedNotificationDto2, expectedNotificationDto1);
    }

    @Test
    void shouldReturnMaxNotificationIdForUser() {
        long expectedNotificationId = 100500L;
        when(notificationRepository.findByUserUid("1234", Pageable.unpaged())).thenReturn(fakeUserNotificationList);

        //when
        long receivedNotificationId = notificationServiceV2.getMaxNotificationIdForUser("1234");

        //then
        assertThat(receivedNotificationId).isEqualTo(expectedNotificationId);
    }

    @Test
    void shouldMarkedNotificationAsRead() {
        Notification notificationToMark = mock(Notification.class);
        when(notificationRepository.findById(999L)).thenReturn(Optional.of(notificationToMark));

        //when
        Throwable call = catchThrowable(() -> notificationServiceV2.markNotificationAsRead(999L));

        //then
        assertThat(call).doesNotThrowAnyException();
        verify(notificationRepository).save(notificationToMark);
    }
}
