package com.fraczekkrzysztof.gocycling.service.notification;

import com.fraczekkrzysztof.gocycling.dao.ClubRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Club;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.Member;
import com.fraczekkrzysztof.gocycling.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NewEventForClubNotificationGeneratorForClubMembersTest {

    @MockBean
    EventRepository eventRepository;
    @MockBean
    ClubRepository clubRepository;
    @MockBean
    NotificationRepository notificationRepository;

    @Autowired
    NewEventForClubNotificationGeneratorForClubMembers notificationGenerator;

    @BeforeEach
    public void before() {

        User ownerOfEventsAndMember = User.builder().id("222").name("Jaroslaw").build();
        User memberUser = User.builder().id("333").name("Zdzisław").build();
        Member member1 = Member.builder().user(ownerOfEventsAndMember).confirmed(true).build();
        Member member2 = Member.builder().user(memberUser).confirmed(true).build();

        Club club = Club.builder()
                .id(10L)
                .name("test club")
                .memberList(Arrays.asList(member1, member2))
                .build();


        Event newEvent = Event.builder()
                .id(12L)
                .name("test1")
                .place("place1")
                .dateAndTime(LocalDateTime.now().plusDays(5))
                .created(LocalDateTime.now())
                .details("Details")
                .canceled(false)
                .club(club)
                .user(ownerOfEventsAndMember)
                .build();

        Set<Long> setOfIds = new HashSet<>();
        setOfIds.add(12L);


        when(eventRepository.findAllById(setOfIds)).thenReturn(Arrays.asList(newEvent));
    }

    @Test
    public void shouldNotThrowAnyExceptionWhenGeneratingNotificationForNewEvent() {
        //given
        notificationGenerator.addEventIdAndIgnoreUser(12, "222");
        //when
        Throwable thrown = catchThrowable(() -> notificationGenerator.generateNotification());
        //then
        assertThat(thrown)
                .doesNotThrowAnyException();
        verify(notificationRepository, times(1)).saveAll(any());
        verify(eventRepository, times(1)).findAllById(any());
    }
}
