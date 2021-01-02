package com.fraczekkrzysztof.gocycling;

import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//@RunWith(SpringRunner.class)
@DataJpaTest
public class EventRepositoryTests {

    @Autowired
    private EventRepository eventRepository;
    //its necessary to prepare data to tests.

//    @Test
//    public void testSaveMethod(){
//        Event event = Event.builder()
//                .name("TestName")
//                .place("TestPlace")
//                .dateAndTime(LocalDateTime.now().plusDays(2))
//                .created((LocalDateTime.now()))
//                .createdBy("1234")
//                .build();
//        eventRepository.save(event);
//        Assert.assertTrue(true);
//        eventRepository.deleteAll();
//    }
//
//    @Test
//    public void findByNameTest(){
//        Event event = Event.builder()
//                .name("TestName")
//                .place("TestPlace")
//                .dateAndTime(LocalDateTime.now().plusDays(2))
//                .created((LocalDateTime.now()))
//                .createdBy("1234")
//                .build();
//        eventRepository.save(event);
//        Event event1 = eventRepository.findByName("TestName",PageRequest.of(0,5)).getContent().get(0);
//        Assert.assertEquals(event.getName(),event1.getName());
//        eventRepository.deleteAll();
//    }
//
//    @Test
//    public void findCurrent(){
//        Event event = Event.builder()
//                .name("TestName")
//                .place("TestPlace")
//                .dateAndTime(LocalDateTime.now().plusDays(2))
//                .created((LocalDateTime.now()))
//                .createdBy("1234")
//                .build();
//        Event event1 = Event.builder()
//                .name("TestName2")
//                .place("TestPlace")
//                .dateAndTime(LocalDateTime.now().plusDays(3))
//                .created((LocalDateTime.now()))
//                .createdBy("1234")
//                .build();
//        eventRepository.save(event);
//        eventRepository.save(event1);
//        List<Event> eventList = eventRepository.findCurrent(PageRequest.of(0,5)).getContent();
//        Assert.assertEquals(2,eventList.size());
//        eventRepository.deleteAll();
//    }
//TODO refactor this after full refactor of application
//    @Test
//    public void findByUserUid(){
//        Event event = Event.builder()
//                .name("TestName")
//                .place("TestPlace")
//                .dateAndTime(LocalDateTime.now().plusDays(2))
//                .created((LocalDateTime.now()))
//                .createdBy("1234")
//                .build();
//        Event event1 = Event.builder()
//                .name("TestName2")
//                .place("TestPlace")
//                .dateAndTime(LocalDateTime.now().plusDays(3))
//                .created((LocalDateTime.now()))
//                .createdBy("1234")
//                .build();
//
//        Confirmation confirmation = Confirmation.builder()
//                                .userUid("test123")
//                                .event(event)
//                                .build();
//        Confirmation confirmation1 = Confirmation.builder()
//                                .userUid("test123")
//                                .event(event1)
//                                .build();
//        eventRepository.save(event);
//        eventRepository.save(event1);
//        confirmationRepository.save(confirmation);
//        confirmationRepository.save(confirmation1);
//        List<Event> eventList = eventRepository.findConfirmedByUserUid("test123", PageRequest.of(0,5)).getContent();
//        Assert.assertEquals(2,eventList.size());
//        eventRepository.deleteAll();
//        confirmationRepository.deleteAll();
//    }
}
