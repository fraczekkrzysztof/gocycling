package com.fraczekkrzysztof.gocycling.mapper.event;

import com.fraczekkrzysztof.gocycling.dto.event.EventDto;
import com.fraczekkrzysztof.gocycling.entity.Club;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventMapper {

    private final ConfirmationMapper confirmationMapper;

    public List<EventDto> mapEventListToEventDtoList(List<Event> eventList, boolean getConfirmations) {
        return eventList.stream().map(e -> mapEventToEventDto(e, getConfirmations)).collect(Collectors.toList());
    }

    public EventDto mapEventToEventDto(Event event, boolean getConfirmations) {
        EventDto.EventDtoBuilder builder = EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .place(event.getPlace())
                .latitude(event.getLatitude())
                .longitude(event.getLongitude())
                .dateAndTime(event.getDateAndTime())
                .created(event.getCreated())
                .updated(event.getUpdated())
                .details(event.getDetails())
                .userId(event.getUser().getId())
                .userName(event.getUser().getName())
                .canceled(event.isCanceled())
                .routeLink(event.getRouteLink())
                .clubId(event.getClub().getId())
                .clubName(event.getClub().getName());
        if (getConfirmations)
            builder.confirmationList(confirmationMapper.mapConfirmationListToConfirmationDtoList(event.getConfirmationList()));
        return builder.build();
    }

    public Event mapEventDtoToEvent(EventDto eventDto, Club club, User user) {
        return Event.builder()
                .id(eventDto.getId())
                .name(eventDto.getName())
                .place(eventDto.getPlace())
                .latitude(eventDto.getLatitude())
                .longitude(eventDto.getLongitude())
                .dateAndTime(eventDto.getDateAndTime())
                .created(eventDto.getCreated())
                .updated(eventDto.getUpdated())
                .details(eventDto.getDetails())
                .user(user)
                .canceled(eventDto.isCanceled())
                .routeLink(eventDto.getRouteLink())
                .club(club)
                .build();


    }
}
