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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
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


    @Test
    public void markAsReadNotification() throws Exception {
        Event event = Event.builder()
                .name("test1")
                .place("place1")
                .dateAndTime(LocalDateTime.now().plusDays(5))
                .created(LocalDateTime.now())
                .details("Details")
                .canceled(false)
                .createdBy("123")
                .build();
        Notification notificationToMarkAsRead = Notification.builder()
                .userUid("123")
                .title("123444")
                .content("ttetrt")
                .created(LocalDateTime.now())
                .read(false)
                .event(event)
                .build();
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(notificationToMarkAsRead));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notificationToMarkAsRead);
        notificationService.markNotificationAsRead(1);
        verify(notificationRepository,times(1)).findById(anyLong());
        verify(notificationRepository,times(1)).save(any(Notification.class));
    }

    @Test
    public void getMaxIdForUSerTest(){
        Event event = Event.builder()
                .name("test1")
                .place("place1")
                .dateAndTime(LocalDateTime.now().plusDays(5))
                .created(LocalDateTime.now())
                .details("Details")
                .canceled(false)
                .createdBy("123")
                .build();
        Notification notification1 = Notification.builder()
                .id(1)
                .userUid("123")
                .title("123444")
                .content("ttetrt")
                .created(LocalDateTime.now())
                .read(false)
                .event(event)
                .build();
        Notification notification2= Notification.builder()
                .id(2)
                .userUid("123")
                .title("123444")
                .content("ttetrt")
                .created(LocalDateTime.now())
                .read(false)
                .event(event)
                .build();
        Page<Notification> pageToReturn = new PageImpl<>(Arrays.asList(notification1,notification2));
        when(notificationRepository.findByUserUid(anyString(),any(Pageable.class))).thenReturn(pageToReturn);

        long id = notificationService.getMaxNotificationIdForUser("123");
        verify(notificationRepository,times(1)).findByUserUid(anyString(),any(Pageable.class));
        Assert.assertEquals(2,id);
    }
}
