package com.fraczekkrzysztof.gocycling;

import com.fraczekkrzysztof.gocycling.dao.ConfirmationRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import com.fraczekkrzysztof.gocycling.service.notification.EventNotificationGeneratorForConfirmations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventNotificationGeneratorTests {

    @MockBean
    EventRepository eventRepository;
    @MockBean
    ConfirmationRepository confirmationRepository;
    @MockBean
    NotificationRepository notificationRepository;

    @Autowired
    @Qualifier("updateEventNotificationGenerator")
    EventNotificationGeneratorForConfirmations notificationGenerator;

    @Before
    public void before(){
        Event eventToList1 = Event.builder()
                .id(1)
                .name("test1")
                .place("place1")
                .dateAndTime(LocalDateTime.now().plusDays(5))
                .created(LocalDateTime.now())
                .details("Details")
                .canceled(false)
                .build();
        Event eventToList2 = Event.builder()
                .id(2)
                .name("test2")
                .place("place2")
                .dateAndTime(LocalDateTime.now().plusDays(5))
                .created(LocalDateTime.now())
                .details("Details")
                .canceled(false)
                .build();

        Confirmation confirmation1 = Confirmation.builder()
                .id(3)
                .userUid("2345432")
                .event(eventToList1).build();

        Confirmation confirmation2 = Confirmation.builder()
                .id(4)
                .userUid("56546453")
                .event(eventToList1).build();

        Confirmation confirmation3 = Confirmation.builder()
                .id(5)
                .userUid("2345432")
                .event(eventToList2).build();

        Confirmation confirmation4 = Confirmation.builder()
                .id(6)
                .userUid("56546453")
                .event(eventToList2).build();

        List<Confirmation> listForEvent1 = Arrays.asList(confirmation1,confirmation2);
        List<Confirmation> listForEvent2 = Arrays.asList(confirmation3,confirmation4);

        when(eventRepository.findAllById(any())).thenReturn(Arrays.asList(eventToList1,eventToList2));
        when(confirmationRepository.findByEventId(1)).thenReturn(listForEvent1);
        when(confirmationRepository.findByEventId(2)).thenReturn(listForEvent2);
        when(notificationRepository.saveAll(any())).thenReturn(Arrays.asList(new Notification(), new Notification()));
    }

    @Test
    public void updateEventNotificationGeneratorTest(){
        notificationGenerator.addEventIdAndIgnoreUser(1,"56546453");
        notificationGenerator.addEventIdAndIgnoreUser(2,null);
        notificationGenerator.generateNotification();
        verify(notificationRepository,times(1)).saveAll(any());
        verify(eventRepository,times(1)).findAllById(any());
    }
}
