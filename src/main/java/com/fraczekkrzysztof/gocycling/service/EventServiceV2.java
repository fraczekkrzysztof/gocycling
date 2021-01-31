package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.event.ConfirmationDto;
import com.fraczekkrzysztof.gocycling.dto.event.EventDto;
import com.fraczekkrzysztof.gocycling.dto.event.EventListResponseDto;
import org.springframework.data.domain.Pageable;

public interface EventServiceV2 {
    EventListResponseDto getEventsList(long clubId, Pageable pageable);

    EventDto getEvent(long eventId);

    EventDto createEvent(long clubId, EventDto eventDto);

    EventDto updateEvent(long eventId, EventDto eventDto);

    void cancelEvent(long eventId);

    ConfirmationDto addConfirmation(long eventId, String userUid);

    void deleteConfirmation(long eventId, String userUid);

    EventListResponseDto getEventsOwnByUser(long clubId, String userUid, Pageable pageable);

    EventListResponseDto getEventsConfirmedByUser(long clubId, String userUid, Pageable pageable);
}
