package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.ClubRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.club.EventDto;
import com.fraczekkrzysztof.gocycling.entity.Club;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.User;
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


}
