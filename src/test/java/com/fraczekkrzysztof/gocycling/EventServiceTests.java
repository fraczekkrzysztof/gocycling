package com.fraczekkrzysztof.gocycling;

import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.service.EventService;
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
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceTests {

    @MockBean
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Before
    public void before(){
        Event eventToCancel = Event.builder()
                .name("test1")
                .place("place1")
                .dateAndTime(LocalDateTime.now().plusDays(5))
                .created(LocalDateTime.now())
                .details("Details")
                .canceled(false)
                .build();
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(eventToCancel));
        when(eventRepository.save(any(Event.class))).thenReturn(eventToCancel);
    }

    @Test
    public void cancelConfirmationTest() throws Exception {
        eventService.cancelEvent(1);
        Assert.assertTrue(true);
    }
}
