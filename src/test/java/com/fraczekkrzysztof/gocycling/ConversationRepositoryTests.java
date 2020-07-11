package com.fraczekkrzysztof.gocycling;


import com.fraczekkrzysztof.gocycling.dao.ConversationRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.entity.Conversation;
import com.fraczekkrzysztof.gocycling.entity.Event;
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
public class ConversationRepositoryTests {

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    EventRepository eventRepository;

    @Test
    public void findByEventId(){
        Event event = Event.builder()
                .name("TestName")
                .place("TestPlace")
                .dateAndTime(LocalDateTime.now().plusDays(2))
                .created((LocalDateTime.now()))
                .createdBy("ziutek")
                .createdBy("12345566").build();

        Conversation conversation1 = Conversation.builder()
                .userUid("12345")
                .username("Testowy Janusz")
                .message("ja dziś odpadam")
                .created(LocalDateTime.now())
                .event(event).build();

        Conversation conversation2 = Conversation.builder()
                .userUid("12345")
                .username("Testowy Ziutek")
                .created(LocalDateTime.now())
                .message("Ja dzis mam ochotę pozadupczać")
                .event(event).build();

        eventRepository.save(event);
        conversationRepository.saveAll(Arrays.asList(conversation1,conversation2));

        long id = event.getId();
        List<Conversation> listOfConversation = conversationRepository.findByEventId(id, PageRequest.of(0,5)).getContent();
        Assert.assertTrue(listOfConversation.stream().anyMatch(c -> {
            return c.getUsername().equals("Testowy Ziutek") || c.getUsername().equals("Testowy Janusz");
        }));

        conversationRepository.deleteAll();
        eventRepository.deleteAll();

    }

}
