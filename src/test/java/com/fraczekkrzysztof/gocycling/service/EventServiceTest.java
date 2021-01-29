package com.fraczekkrzysztof.gocycling.service;


import com.fraczekkrzysztof.gocycling.dao.ClubRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.event.ConfirmationDto;
import com.fraczekkrzysztof.gocycling.dto.event.EventDto;
import com.fraczekkrzysztof.gocycling.entity.Club;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.mapper.event.ConfirmationMapper;
import com.fraczekkrzysztof.gocycling.mapper.event.EventMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EventServiceTest {

    @MockBean
    EventRepository eventRepository;
    @MockBean
    ClubRepository clubRepository;
    @MockBean
    UserRepository userRepository;
    @Autowired
    EventMapper eventMapper;
    @Autowired
    ConfirmationMapper confirmationMapper;

    @Autowired
    EventServiceV2 eventService;

    List<Event> fakeEventsList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        User user1 = User.builder()
                .id("123")
                .name("Testowy Jan").build();
        User user2 = User.builder()
                .id("456")
                .name("Testowy Edward").build();
        User user3 = User.builder()
                .id("789")
                .name("Testowy Wiesław").build();

        Confirmation cofirmationUser1Event1 = Confirmation.builder().id(1L).user(user1).build();
        Confirmation cofirmationUser2Event1 = Confirmation.builder().id(2L).user(user2).build();
        Confirmation cofirmationUser2Event2 = Confirmation.builder().id(3L).user(user3).build();
        Confirmation cofirmationUser3Event2 = Confirmation.builder().id(4L).user(user3).build();

        Club fakeClub = Club.builder()
                .id(20L)
                .name("Some fake club")
                .created(LocalDateTime.of(2020, 11, 01, 12, 00))
                .location("LocationOfClub2")
                .details("Super Club 2")
                .longitude(18.1)
                .latitude(19.1)
                .user(user1)
                .memberList(new ArrayList<>())
                .privateMode(false)
                .build();

        Event event1 = Event.builder()
                .id(1L)
                .name("Super event 1")
                .place("Super place 1")
                .longitude(13.5)
                .latitude(55.2)
                .user(user1)
                .dateAndTime(LocalDateTime.now().plusDays(1))
                .club(fakeClub)
                .details("Super details of event 1")
                .created(LocalDateTime.of(2020, 01, 01, 8, 00))
                .confirmationList(Arrays.asList(cofirmationUser1Event1, cofirmationUser2Event1))
                .canceled(false)
                .build();

        Event event2 = Event.builder()
                .id(2L)
                .name("Super event 2")
                .place("Super place 2")
                .longitude(53.5)
                .latitude(75.2)
                .user(user2)
                .dateAndTime(LocalDateTime.now().plusDays(2))
                .club(fakeClub)
                .details("Super details of event 2")
                .created(LocalDateTime.of(2020, 01, 02, 8, 00))
                .confirmationList(Arrays.asList(cofirmationUser2Event2, cofirmationUser3Event2))
                .canceled(false)
                .build();

        Event event3 = Event.builder()
                .id(3L)
                .name("Super event 3")
                .place("Super place 3")
                .latitude(53.5)
                .latitude(75.2)
                .user(user1)
                .dateAndTime(LocalDateTime.now().plusDays(10))
                .club(fakeClub)
                .details("Super details of event 3")
                .created(LocalDateTime.of(2020, 01, 02, 8, 00))
                .canceled(true)
                .build();

        fakeEventsList.addAll(Arrays.asList(event1, event2, event3));
    }

    @Test
    void shouldRetrieveTwoEvents() {
        //given
        when(eventRepository.findByClubId(20)).thenReturn(fakeEventsList);
        EventDto expectedEvent1 = EventDto.builder()
                .id(1L)
                .clubId(20L)
                .clubName("Some fake club")
                .canceled(false)
                .dateAndTime(LocalDateTime.now().plusDays(1))
                .created(LocalDateTime.of(2020, 01, 01, 8, 00))
                .details("Super details of event 1")
                .name("Super event 1")
                .place("Super place 1")
                .longitude(13.5)
                .latitude(55.2)
                .userId("123")
                .userName("Testowy Jan")
                .build();
        EventDto expectedEvent2 = EventDto.builder()
                .id(2L)
                .clubId(20L)
                .clubName("Some fake club")
                .canceled(false)
                .dateAndTime(LocalDateTime.now().plusDays(1))
                .created(LocalDateTime.of(2020, 01, 02, 8, 00))
                .details("Super details of event 2")
                .name("Super event 2")
                .place("Super place 2")
                .longitude(53.5)
                .latitude(75.2)
                .userId("456")
                .userName("Testowy Edward")
                .build();
        //when
        List<EventDto> retrievedEventList = eventService.getEventsList(20);
        //then
        assertThat(retrievedEventList).hasSize(2);
        assertThat(retrievedEventList).usingElementComparatorIgnoringFields("dateAndTime").containsExactlyInAnyOrder(expectedEvent1, expectedEvent2);
    }

    @Test
    void shouldRetrieveOneEvent() {
        //given
        when(eventRepository.findById(1L)).thenReturn(Optional.of(fakeEventsList.get(0)));
        ConfirmationDto confirmation1 = ConfirmationDto.builder()
                .id(1L)
                .userId("123")
                .userName("Testowy Jan")
                .build();
        ConfirmationDto confirmation2 = ConfirmationDto.builder()
                .id(2L)
                .userId("456")
                .userName("Testowy Edward")
                .build();
        EventDto expectedEvent = EventDto.builder()
                .id(1L)
                .clubId(20L)
                .clubName("Some fake club")
                .canceled(false)
                .dateAndTime(LocalDateTime.now().plusDays(1))
                .created(LocalDateTime.of(2020, 01, 01, 8, 00))
                .details("Super details of event 1")
                .name("Super event 1")
                .place("Super place 1")
                .longitude(13.5)
                .latitude(55.2)
                .userId("123")
                .confirmationList(Arrays.asList(confirmation1, confirmation2))
                .userName("Testowy Jan")
                .build();

        //when
        EventDto retrievedEvent = eventService.getEvent(1L);

        //then
        assertThat(retrievedEvent).usingRecursiveComparison().ignoringFields("dateAndTime").isEqualTo(expectedEvent);
    }

    @Test
    void shouldCreateEvent() {
        //given
        User fakeUser = User.builder().id("456").name("Zdzisław Burczymucha").build();
        Club fakeClub = Club.builder().id(30L).name("Fake club").build();
        when(clubRepository.findById(30L)).thenReturn(Optional.of(fakeClub));
        when(userRepository.findById("456")).thenReturn(Optional.of(fakeUser));
        LocalDateTime dateTimeOfEvent = LocalDateTime.now().plusDays(10);
        EventDto givenEvent = EventDto.builder()
                .dateAndTime(dateTimeOfEvent)
                .created(LocalDateTime.of(2020, 01, 01, 8, 00))
                .details("Super details")
                .name("Extra Event")
                .place("In super place")
                .longitude(13.5)
                .latitude(55.2)
                .userId("456")
                .build();
        EventDto expectedEvent = EventDto.builder()
                .clubId(30L)
                .clubName("Fake club")
                .canceled(false)
                .dateAndTime(dateTimeOfEvent)
                .created(LocalDateTime.of(2020, 01, 01, 8, 00))
                .details("Super details")
                .name("Extra Event")
                .place("In super place")
                .longitude(13.5)
                .latitude(55.2)
                .userId("456")
                .userName("Zdzisław Burczymucha")
                .build();

        //when
        EventDto createdEvent = eventService.createEvent(30L, givenEvent);

        //then
        assertThat(createdEvent).usingRecursiveComparison().ignoringFields("id", "created").isEqualTo(expectedEvent);
    }

    @Test
    void shouldUpdateEvent() {
        User fakeUser = User.builder().id("456").name("Zdzisław Burczymucha").build();
        Club fakeClub = Club.builder().id(30L).name("Fake club").build();
        Event eventToUpdate = Event.builder()
                .id(789L)
                .dateAndTime(LocalDateTime.of(2021, 01, 01, 10, 00))
                .created(LocalDateTime.of(2020, 01, 01, 8, 00))
                .details("Super details")
                .name("Extra Event")
                .place("In super place")
                .longitude(13.5)
                .latitude(55.2)
                .user(fakeUser)
                .club(fakeClub)
                .canceled(false)
                .build();
        when(eventRepository.findById(789L)).thenReturn(Optional.of(eventToUpdate));
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(10);
        EventDto dataGivenToUpdate = EventDto.builder()
                .name("Shiny new name")
                .place("New shiny place")
                .latitude(45.00)
                .longitude(55.00)
                .dateAndTime(newDateTime)
                .details("Amazing updated event")
                .build();
        EventDto expectedEventDto = EventDto.builder()
                .id(789L)
                .created(LocalDateTime.of(2020, 01, 01, 8, 00))
                .dateAndTime(newDateTime)
                .details("Amazing updated event")
                .name("Shiny new name")
                .place("New shiny place")
                .latitude(45.00)
                .longitude(55.00)
                .userName("Zdzisław Burczymucha")
                .userId("456")
                .clubName("Fake club")
                .clubId(30L)
                .canceled(false)
                .build();

        //when
        EventDto updatedEvent = eventService.updateEvent(789L, dataGivenToUpdate);
        //then
        assertThat(updatedEvent).usingRecursiveComparison().ignoringFields("updated").isEqualTo(expectedEventDto);
        assertThat(updatedEvent.getUpdated()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.MINUTES));
    }

    @Test
    void shouldCancelEvent() {
        //given
        Event eventToCancel = mock(Event.class);
        User mockUser = mock(User.class);
        when(mockUser.getId()).thenReturn("12345L");
        when(eventToCancel.getUser()).thenReturn(mockUser);
        when(eventRepository.findById(333L)).thenReturn(Optional.of(eventToCancel));

        //when
        Throwable call = catchThrowable(() -> eventService.cancelEvent(333L));
        //then
        assertThat(call).doesNotThrowAnyException();
        verify(eventRepository).save(eventToCancel);
    }

    @Test
    void shouldAddConfirmation() {
        //given
        User userWithConfirmation = mock(User.class);
        when(userWithConfirmation.getId()).thenReturn("111");
        Confirmation existingConfirmation = mock(Confirmation.class);
        when(existingConfirmation.getUser()).thenReturn(userWithConfirmation);
        List<Confirmation> mockExistingConfirmationList = new ArrayList<>();
        mockExistingConfirmationList.add(existingConfirmation);
        Event mockEvent = mock(Event.class);
        when(mockEvent.getConfirmationList()).thenReturn(mockExistingConfirmationList);
        when(eventRepository.findById(999L)).thenReturn(Optional.of(mockEvent));
        User userWhichWantConfirm = mock(User.class);
        when(userWhichWantConfirm.getId()).thenReturn("222");
        when(userWhichWantConfirm.getName()).thenReturn("Dobry Ziom");
        when(userRepository.findById("222")).thenReturn(Optional.of(userWhichWantConfirm));
        ConfirmationDto expectedCOnfirmation = ConfirmationDto.builder()
                .userName("Dobry Ziom")
                .userId("222")
                .build();

        //when
        ConfirmationDto addedConfirmation = eventService.addConfirmation(999, "222");
        //then
        assertThat(addedConfirmation).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedCOnfirmation);
    }

    @Test
    void shouldDeleteUserConfirmation() {
        //given
        User randomNotImportantUser = mock(User.class);
        when(randomNotImportantUser.getId()).thenReturn("123");
        Confirmation randomNotImportantConfirmation = mock(Confirmation.class);
        when(randomNotImportantConfirmation.getUser()).thenReturn(randomNotImportantUser);
        User userWithConformationToDelete = mock(User.class);
        when(userWithConformationToDelete.getId()).thenReturn("333");
        Confirmation confirmationToDelete = mock(Confirmation.class);
        when(confirmationToDelete.getUser()).thenReturn(userWithConformationToDelete);
        List<Confirmation> confirmationList = new ArrayList<>();
        confirmationList.addAll(Arrays.asList(randomNotImportantConfirmation, confirmationToDelete));
        Event mockEvent = mock(Event.class);
        when(mockEvent.getConfirmationList()).thenReturn(confirmationList);
        when(eventRepository.findById(999L)).thenReturn(Optional.of(mockEvent));
        //when
        Throwable call = catchThrowable(() -> eventService.deleteConfirmation(999L, "333"));
        //then
        assertThat(call).doesNotThrowAnyException();
    }
}
