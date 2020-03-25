package com.fraczekkrzysztof.gocycling;

import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import com.fraczekkrzysztof.gocycling.service.NotificationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationServiceTests {

    @MockBean
    NotificationRepository notificationRepository;

    @Autowired
    NotificationService notificationService;

    @Before
    public void before(){
        Event event = new Event.Builder()
                .setName("test1")
                .setPlace("place1")
                .setDateAndTime(LocalDateTime.now().plusDays(5))
                .setCreated(LocalDateTime.now())
                .setDetails("Details")
                .setCanceled(false)
                .setCreatedBy("123")
                .build();
        Notification notificationToMarkAsRead = new Notification.Builder()
                .setUserUid("123")
                .setTitle("123444")
                .setContent("ttetrt")
                .setCreated(LocalDateTime.now())
                .setRead(false)
                .setEvent(event)
                .build();
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(notificationToMarkAsRead));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notificationToMarkAsRead);
    }

    @Test
    public void markAsReadNotification() throws Exception {
        notificationService.markNotificationAsRead(1);
        verify(notificationRepository,times(1)).findById(anyLong());
        verify(notificationRepository,times(1)).save(any(Notification.class));
    }
}
