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
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventRepositoryTests {

    @Autowired
    private EventRepository eventRepository;
    //its necessary to prepare data to tests.
    @Autowired
    private ConfirmationRepository confirmationRepository;

    @Test
    public void testSaveMethod(){
        Event event = new Event.Builder()
                .setName("TestName")
                .setPlace("TestPlace")
                .setDateAndTime(LocalDateTime.now().plusDays(2))
                .setCreated((LocalDateTime.now())).build();
        eventRepository.save(event);
        Assert.assertTrue(true);
        eventRepository.deleteAll();
    }

    @Test
    public void findByNameTest(){
        Event event = new Event.Builder()
                .setName("TestName")
                .setPlace("TestPlace")
                .setDateAndTime(LocalDateTime.now().plusDays(2))
                .setCreated((LocalDateTime.now())).build();
        eventRepository.save(event);
        Event event1 = eventRepository.findByName("TestName",PageRequest.of(0,5)).getContent().get(0);
        Assert.assertEquals(event.getName(),event1.getName());
        eventRepository.deleteAll();
    }

    @Test
    public void findCurrent(){
        Event event = new Event.Builder()
                .setName("TestName")
                .setPlace("TestPlace")
                .setDateAndTime(LocalDateTime.now().plusDays(2))
                .setCreated((LocalDateTime.now())).build();
        Event event1 = new Event.Builder()
                .setName("TestName2")
                .setPlace("TestPlace")
                .setDateAndTime(LocalDateTime.now().plusDays(3))
                .setCreated((LocalDateTime.now())).build();
        eventRepository.save(event);
        eventRepository.save(event1);
        List<Event> eventList = eventRepository.findCurrent(PageRequest.of(0,5)).getContent();
        Assert.assertEquals(2,eventList.size());
        eventRepository.deleteAll();
    }

    @Test
    public void findByUserUid(){
        Event event = new Event.Builder()
                .setName("TestName")
                .setPlace("TestPlace")
                .setDateAndTime(LocalDateTime.now().plusDays(2))
                .setCreated((LocalDateTime.now())).build();
        Event event1 = new Event.Builder()
                .setName("TestName2")
                .setPlace("TestPlace")
                .setDateAndTime(LocalDateTime.now().plusDays(3))
                .setCreated((LocalDateTime.now())).build();

        Confirmation confirmation = new Confirmation.Builder()
                                .serUserUid("test123")
                                .setEvent(event)
                                .build();
        Confirmation confirmation1 = new Confirmation.Builder()
                                .serUserUid("test123")
                                .setEvent(event1)
                                .build();
        eventRepository.save(event);
        eventRepository.save(event1);
        confirmationRepository.save(confirmation);
        confirmationRepository.save(confirmation1);
        List<Event> eventList = eventRepository.findConfirmedByUserUid("test123", PageRequest.of(0,5)).getContent();
        Assert.assertEquals(2,eventList.size());
        eventRepository.deleteAll();
        confirmationRepository.deleteAll();
    }
}
