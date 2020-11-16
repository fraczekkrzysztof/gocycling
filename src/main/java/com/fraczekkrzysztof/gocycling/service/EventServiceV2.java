package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.event.ConfirmationDto;
import com.fraczekkrzysztof.gocycling.dto.event.EventDto;

import java.util.List;

public interface EventServiceV2 {
    List<EventDto> getEventsList(long clubId);

    EventDto getEvent(long eventId);

    EventDto createEvent(long clubId, EventDto eventDto);

    EventDto updateEvent(long eventId, EventDto eventDto);

    void cancelEvent(long eventId);

    ConfirmationDto addConfirmation(long eventId, String userUid);

    void deleteConfirmation(long eventId, String userUid);

    List<EventDto> getEventsOwnByUser(long clubId, String userUid);

    List<EventDto> getEventsConfirmedByUser(long clubId, String userUid);
}
