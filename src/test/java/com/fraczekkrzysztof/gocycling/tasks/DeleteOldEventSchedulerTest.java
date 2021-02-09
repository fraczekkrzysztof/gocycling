package com.fraczekkrzysztof.gocycling.tasks;


import com.fraczekkrzysztof.gocycling.GoCyclingProperties;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DeleteOldEventSchedulerTest {

    @MockBean
    GoCyclingProperties properties;
    @MockBean
    EventRepository eventRepository;
    @MockBean
    NotificationRepository notificationRepository;

    @Autowired
    DeleteOldEventScheduler deleteOldEventScheduler;

    @Test
    void shouldNotThrowAnyExceptionWhenDeletingOldEventsAndNotifications() {
        Event event1 = mock(Event.class);
        when(event1.getId()).thenReturn(10L);
        Event event2 = mock(Event.class);
        when(event2.getId()).thenReturn(20L);
        List<Event> listOfEvents = new ArrayList<>(Arrays.asList(event1, event2));
        when(eventRepository.findEventsOlderThan(any(LocalDateTime.class), eq(Pageable.unpaged()))).thenReturn(new PageImpl<>(listOfEvents));
        Notification notification1 = mock(Notification.class);
        List<Notification> mockNotificationList = new ArrayList<>(Arrays.asList(notification1));
        when(notificationRepository.findByEventIdList(Arrays.asList(10L, 20L), Pageable.unpaged())).thenReturn(new PageImpl<>(mockNotificationList));

        //when
        Throwable call = catchThrowable(() -> deleteOldEventScheduler.deleteOldEvents());

        //then
        assertThat(call).doesNotThrowAnyException();
        verify(eventRepository).deleteAll(listOfEvents);
        verify(notificationRepository).deleteAll(mockNotificationList);
    }
}
