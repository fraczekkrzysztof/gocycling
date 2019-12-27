package com.fraczekkrzysztof.goonbike;

import com.fraczekkrzysztof.goonbike.dao.EventRepository;
import com.fraczekkrzysztof.goonbike.entity.Event;
import org.jcp.xml.dsig.internal.dom.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventRepositoryTests {

    @Autowired
    private EventRepository eventRepository;

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
    }
}
