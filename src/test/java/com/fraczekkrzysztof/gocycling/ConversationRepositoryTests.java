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
import org.springframework.data.domain.Page;
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
        Event event = new Event.Builder()
                .setName("TestName")
                .setPlace("TestPlace")
                .setDateAndTime(LocalDateTime.now().plusDays(2))
                .setCreated((LocalDateTime.now()))
                .setCreatedBy("12345566").build();

        Conversation conversation1 = new Conversation.Builder()
                .setUserUid("12345")
                .setUsername("Testowy Janusz")
                .setMessage("ja dziś odpadam")
                .setEvent(event).build();

        Conversation conversation2 = new Conversation.Builder()
                .setUserUid("12345")
                .setUsername("Testowy Ziutek")
                .setMessage("Ja dzis mam ochotę pozadupczać")
                .setEvent(event).build();

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
