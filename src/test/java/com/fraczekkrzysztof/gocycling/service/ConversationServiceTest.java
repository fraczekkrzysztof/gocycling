package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.ConversationRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.event.ConversationDto;
import com.fraczekkrzysztof.gocycling.dto.event.ConversationListResponseDto;
import com.fraczekkrzysztof.gocycling.dto.event.ConversationResponseDto;
import com.fraczekkrzysztof.gocycling.entity.Conversation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ConversationServiceTest {

    @MockBean
    ConversationRepository conversationRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    EventRepository eventRepository;

    @Autowired
    ConversationServiceV2 conversationService;

    @Test
    void shouldReturnConversationDtoList() {
        User user1 = User.builder().name("Jarosław").id("123").build();
        User user2 = User.builder().name("Mieczysłąw").id("456").build();

        Conversation conversation1 = Conversation.builder()
                .created(LocalDateTime.of(2021, 01, 19, 12, 00, 00))
                .id(1L)
                .message("Może podkręcimy trochę tempo?")
                .user(user1).build();

        Conversation conversation2 = Conversation.builder()
                .created(LocalDateTime.of(2021, 01, 19, 12, 01, 00))
                .id(2L)
                .message("Panie mamy styczeń! Tlen trzeba robić!")
                .user(user2).build();

        when(conversationRepository.findByEventId(35L, PageRequest.of(0, 20))).thenReturn(new PageImpl<>(Arrays.asList(conversation1, conversation2)));

        ConversationDto expectedConversation1 = ConversationDto.builder()
                .id(1L)
                .created(LocalDateTime.of(2021, 01, 19, 12, 00, 00))
                .userId("123")
                .userName("Jarosław")
                .message("Może podkręcimy trochę tempo?")
                .build();
        ConversationDto expectedConversation2 = ConversationDto.builder()
                .id(2L)
                .created(LocalDateTime.of(2021, 01, 19, 12, 01, 00))
                .userId("456")
                .userName("Mieczysłąw")
                .message("Panie mamy styczeń! Tlen trzeba robić!")
                .build();

        //when
        ConversationListResponseDto receivedConversationList = conversationService.getAllByEventId(35L, PageRequest.of(0, 20));

        //then
        assertThat(receivedConversationList.getConversations()).containsExactlyInAnyOrder(expectedConversation1, expectedConversation2);
    }

    @Test
    void shouldAddConversation() {
        Event mockEvent = mock(Event.class);
        when(eventRepository.findById(4444L)).thenReturn(Optional.of(mockEvent));
        User userWhichWantToAddConversation = mock(User.class);
        when(userWhichWantToAddConversation.getId()).thenReturn("1234");
        when(userWhichWantToAddConversation.getName()).thenReturn("Mieczysław");
        when(userRepository.findById("1234")).thenReturn(Optional.of(userWhichWantToAddConversation));
        ConversationDto givenConversationDto = ConversationDto.builder()
                .userId("1234")
                .message("Dodaje sobie wiadomość")
                .build();

        ConversationDto expectedConversationDto = ConversationDto.builder()
                .message("Dodaje sobie wiadomość")
                .userId("1234")
                .userName("Mieczysław")
                .build();

        //when
        ConversationResponseDto returnedConversationDto = conversationService.addConversation(4444L, givenConversationDto);

        //then
        assertThat(returnedConversationDto.getConversation()).usingRecursiveComparison().ignoringFields("id", "created").isEqualTo(expectedConversationDto);
        assertThat(returnedConversationDto.getConversation().getCreated()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.MINUTES));
        assertThat(returnedConversationDto.getConversation().getId()).isNotNull();
    }
}
