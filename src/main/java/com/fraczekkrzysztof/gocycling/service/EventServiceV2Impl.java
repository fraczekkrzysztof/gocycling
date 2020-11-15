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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceV2Impl implements EventServiceV2 {

    private final EventRepository eventRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final ConfirmationMapper confirmationMapper;


    @Override
    public List<EventDto> getEventsList(long clubId) {
        List<Event> eventList = filterEventList(eventRepository.findByClubId(clubId));
        return eventMapper.mapEventListToEventDtoList(eventList, false);
    }

    private List<Event> filterEventList(List<Event> eventList) {
        return eventList.stream().filter(e -> !e.isCanceled()).filter(e -> e.getDateAndTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());
    }

    @Override
    public EventDto getEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException(String.format("There is no event of id %d", eventId)));
        return eventMapper.mapEventToEventDto(event, true);
    }

    @Override
    public EventDto createEvent(long clubId, EventDto eventDto) {
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
        return eventMapper.mapEventToEventDto(eventToSave, false);
    }

    @Override
    public EventDto updateEvent(long eventId, EventDto eventDto) {
        Event eventToUpdate = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no event of id %d", eventId)));
        modifyEvent(eventToUpdate, eventDto);
        eventToUpdate.setUpdated(LocalDateTime.now());
        eventRepository.save(eventToUpdate);
        return eventMapper.mapEventToEventDto(eventToUpdate, false);
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
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no event of id %d", eventId)));
        eventToCancel.setCanceled(true);
        eventRepository.save(eventToCancel);
    }

    @Override
    public ConfirmationDto addConfirmation(long eventId, String userUid) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no event of id %d", eventId)));
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
        return confirmationMapper.mapConfirmationToConfirmationDto(addedConfirmation);
    }

    @Override
    public void deleteConfirmation(long eventId, String userUid) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no event of id %d", eventId)));
        Confirmation confirmationToRemove = event.getConfirmationList()
                .stream()
                .filter(c -> c.getUser().getId().equals(userUid))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no confirmation of event %d, for user %s", eventId, userUid)));
        event.getConfirmationList().remove(confirmationToRemove);
        eventRepository.save(event);
    }
}
