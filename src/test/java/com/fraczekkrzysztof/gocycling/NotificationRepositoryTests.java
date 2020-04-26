package com.fraczekkrzysztof.gocycling;

import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NotificationRepositoryTests {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    EventRepository eventRepository;

    @Test
    public void shouldReturnTwoNotification(){
        Event event = Event.builder().name("Test1").place("TestPlace1").created(LocalDateTime.now())
                .dateAndTime(LocalDateTime.now().plusDays(5)).createdBy("456").build();
        Notification not1 = Notification.builder()
                .userUid("123")
                .title("test1")
                .content("test123")
                .created(LocalDateTime.now())
                .event(event).build();

        Notification not2 = Notification.builder()
                .userUid("123")
                .title("test112")
                .content("test123456")
                .created(LocalDateTime.now())
                .event(event).build();

        eventRepository.save(event);
        notificationRepository.saveAll(Arrays.asList(not1,not2));
        List<Notification> notlist = notificationRepository.findByUserUid("123", PageRequest.of(0,5)).getContent();
        Assert.assertTrue(notlist.size()==2);
    }
}
