package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.ClubRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.event.*;
import com.fraczekkrzysztof.gocycling.entity.Club;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.mapper.event.ConfirmationMapper;
import com.fraczekkrzysztof.gocycling.mapper.event.EventMapper;
import com.fraczekkrzysztof.gocycling.paging.PageDto;
import com.fraczekkrzysztof.gocycling.paging.PagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceV2Impl implements EventServiceV2 {

    public static final String NO_EVENT_ERROR_MESSAGE = "There is no event of id %d";
    private final EventRepository eventRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final ConfirmationMapper confirmationMapper;
    private final PagingService pagingService;

    @Override
    public EventListResponseDto getEventsList(long clubId, Pageable pageable) {
        Page<Event> pagedEvents = eventRepository.findInFutureByClubId(clubId, LocalDateTime.now(), pageable);
        return mapEventsPageToEventListResponseDto(pagedEvents);
    }

    private EventListResponseDto mapEventsPageToEventListResponseDto(Page<Event> page) {
        PageDto pageDto = pagingService.generatePageInfo(page);
        List<EventDto> listOfMappedEvents = eventMapper.mapEventListToEventDtoList(page.getContent(), false);
        return EventListResponseDto.builder().events(listOfMappedEvents).page(pageDto).build();
    }

    @Override
    public EventResponseDto getEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException(String.format(NO_EVENT_ERROR_MESSAGE, eventId)));
        EventDto mappedEvent = eventMapper.mapEventToEventDto(event, true);
        return EventResponseDto.builder().event(mappedEvent).build();
    }

    @Override
    public EventResponseDto createEvent(long clubId, EventDto eventDto) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no club of id %d", clubId)));
        User user = userRepository.findById(eventDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no user if id %s", eventDto.getUserId())));
        if (eventDto.getDateAndTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("You can't create event in past");
        }
        eventDto.setId(0L);
        eventDto.setCreated(LocalDateTime.now());
        eventDto.setUpdated(null);
        Event eventToSave = eventMapper.mapEventDtoToEvent(eventDto, club, user);
        eventRepository.save(eventToSave);
        EventDto mappedAddedEvent = eventMapper.mapEventToEventDto(eventToSave, false);
        return EventResponseDto.builder().event(mappedAddedEvent).build();
    }

    @Override
    public EventResponseDto updateEvent(long eventId, EventDto eventDto) {
        Event eventToUpdate = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException(String.format(NO_EVENT_ERROR_MESSAGE, eventId)));
        modifyEvent(eventToUpdate, eventDto);
        eventToUpdate.setUpdated(LocalDateTime.now());
        eventRepository.save(eventToUpdate);
        EventDto mappedUpdatedEvent = eventMapper.mapEventToEventDto(eventToUpdate, false);
        return EventResponseDto.builder().event(mappedUpdatedEvent).build();
    }

    private void modifyEvent(Event event, EventDto eventDto) {
        event.setName(eventDto.getName());
        event.setPlace(eventDto.getPlace());
        event.setLatitude(eventDto.getLatitude());
        event.setLongitude(eventDto.getLongitude());
        event.setDateAndTime(eventDto.getDateAndTime());
        event.setDetails(eventDto.getDetails());
        event.setRouteLink(eventDto.getRouteLink());
    }

    @Override
    public void cancelEvent(long eventId) {
        Event eventToCancel = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException(String.format(NO_EVENT_ERROR_MESSAGE, eventId)));
        eventToCancel.setCanceled(true);
        eventRepository.save(eventToCancel);
    }

    @Override
    public ConfirmationResponse addConfirmation(long eventId, String userUid) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException(String.format(NO_EVENT_ERROR_MESSAGE, eventId)));
        User user = userRepository.findById(userUid)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no user of id %s", userUid)));
        Confirmation confirmationToAdd = Confirmation.builder()
                .id(0)
                .user(user)
                .build();
        event.getConfirmationList().add(confirmationToAdd);
        eventRepository.save(event);
        //retrieve last added confirmation
        Confirmation addedConfirmation = event.getConfirmationList().stream().filter(c -> c.getUser().getId().equals(userUid))
                .findFirst().orElseThrow(() -> new NoSuchElementException(String.format("Confirmation for user %s wasn't added", userUid)));
        ConfirmationDto mappedAddedConfirmation = confirmationMapper.mapConfirmationToConfirmationDto(addedConfirmation);
        return ConfirmationResponse.builder().confirmation(mappedAddedConfirmation).build();
    }

    @Override
    public void deleteConfirmation(long eventId, String userUid) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException(String.format(NO_EVENT_ERROR_MESSAGE, eventId)));
        Confirmation confirmationToRemove = event.getConfirmationList()
                .stream()
                .filter(c -> c.getUser().getId().equals(userUid))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no confirmation of event %d, for user %s", eventId, userUid)));
        event.getConfirmationList().remove(confirmationToRemove);
        eventRepository.save(event);
    }

    @Override
    public EventListResponseDto getEventsOwnByUser(long clubId, String userUid, Pageable pageable) {
        Page<Event> pagedEvents = eventRepository.findByClubIdAndOwner(clubId, userUid, LocalDateTime.now(), pageable);
        return mapEventsPageToEventListResponseDto(pagedEvents);
    }

    @Override
    public EventListResponseDto getEventsConfirmedByUser(long clubId, String userUid, Pageable pageable) {
        Page<Event> pagedEvents = eventRepository.findByClubIdAndUserConfirmation(clubId, userUid, LocalDateTime.now(), pageable);
        return mapEventsPageToEventListResponseDto(pagedEvents);
    }


}
