package com.fraczekkrzysztof.gocycling;

import com.fraczekkrzysztof.gocycling.dao.ConfirmationRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConfirmationRepositoryTests {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ConfirmationRepository confirmationRepository;

    @Test
    public void findByUSerUid(){
        Event event = new Event.Builder()
                .setName("TestName")
                .setPlace("TestPlace")
                .setDateAndTime(LocalDateTime.now().plusDays(2))
                .setCreated((LocalDateTime.now())).build();
        Confirmation confirmation = new Confirmation.Builder().serUserUid("test123").setEvent(event).build();
        eventRepository.save(event);
        confirmationRepository.save(confirmation);
        Confirmation confirmation1 = confirmationRepository.findByUserUid("test123", PageRequest.of(0,5)).getContent().get(0);
        Assert.assertEquals(confirmation.getUserUid(),confirmation1.getUserUid());
        confirmationRepository.deleteAll();
        eventRepository.deleteAll();
    };

    @Test
    public void findByUserUidAndEventId(){
        Event event = new Event.Builder()
                .setName("TestName")
                .setPlace("TestPlace")
                .setDateAndTime(LocalDateTime.now().plusDays(2))
                .setCreated((LocalDateTime.now())).build();
        Confirmation confirmation = new Confirmation.Builder().serUserUid("test123").setEvent(event).build();
        eventRepository.save(event);
        confirmationRepository.save(confirmation);
        long eventId = event.getId();
        Confirmation confirmation1 = confirmationRepository.findByUserUidAndEventId("test123", eventId, PageRequest.of(0,5)).getContent().get(0);
        Assert.assertEquals(confirmation.getUserUid(),confirmation1.getUserUid());
        Assert.assertEquals(confirmation.getEvent().getName(),confirmation1.getEvent().getName());
        confirmationRepository.deleteAll();
        eventRepository.deleteAll();
    };

}
