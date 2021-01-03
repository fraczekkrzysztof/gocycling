package com.fraczekkrzysztof.gocycling.service.notification;

import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Club;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateEventNotificationGeneratorForConfirmationTest {


    @Mock
    EventRepository eventRepository;
    @Mock
    NotificationRepository notificationRepository;

    EventNotificationGeneratorForConfirmations notificationGenerator;

    @BeforeEach
    public void before() {
        notificationGenerator = new UpdateEventNotificationGeneratorForConfirmation(eventRepository, notificationRepository);
        Club club = Club.builder()
                .id(10L)
                .build();
        Event eventToList1 = Event.builder()
                .id(1)
                .name("test1")
                .place("place1")
                .dateAndTime(LocalDateTime.now().plusDays(5))
                .created(LocalDateTime.now())
                .details("Details")
                .canceled(false)
                .club(club)
                .build();
        Event eventToList2 = Event.builder()
                .id(2)
                .name("test2")
                .place("place2")
                .dateAndTime(LocalDateTime.now().plusDays(5))
                .created(LocalDateTime.now())
                .details("Details")
                .canceled(false)
                .club(club)
                .build();

        User user1 = User.builder().id("2345432").name("Jan").build();
        User user2 = User.builder().id("56546453").name("Franciszek").build();
        Confirmation confirmation1 = Confirmation.builder()
                .id(3)
                .user(user1)
                .build();

        Confirmation confirmation2 = Confirmation.builder()
                .id(4)
                .user(user2)
                .build();

        Confirmation confirmation3 = Confirmation.builder()
                .id(5)
                .user(user1)
                .build();

        Confirmation confirmation4 = Confirmation.builder()
                .id(6)
                .user(user2)
                .build();

        List<Confirmation> listForEvent1 = Arrays.asList(confirmation1, confirmation2);
        List<Confirmation> listForEvent2 = Arrays.asList(confirmation3, confirmation4);

        eventToList1.setConfirmationList(listForEvent1);
        eventToList2.setConfirmationList(listForEvent2);

        Set<Long> setOfIds = new HashSet<>();
        setOfIds.add(1L);
        setOfIds.add(2L);

        when(eventRepository.findAllById(setOfIds)).thenReturn(Arrays.asList(eventToList1, eventToList2));
    }

    @Test
    public void shouldNotThrowAnyExceptionWhenGeneratingNotificationForUpdateEvent() {
        //given
        notificationGenerator.addEventIdAndIgnoreUser(1, "56546453");
        notificationGenerator.addEventIdAndIgnoreUser(2, null);

        //when
        Throwable thrown = catchThrowable(() -> notificationGenerator.generateNotification());
        //then
        assertThat(thrown)
                .doesNotThrowAnyException();
        verify(notificationRepository, times(1)).saveAll(any());
        verify(eventRepository, times(1)).findAllById(any());
    }
}
