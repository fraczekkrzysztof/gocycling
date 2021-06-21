package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.event.ConfirmationResponse;
import com.fraczekkrzysztof.gocycling.dto.event.EventDto;
import com.fraczekkrzysztof.gocycling.dto.event.EventListResponseDto;
import com.fraczekkrzysztof.gocycling.dto.event.EventResponseDto;
import org.springframework.data.domain.Pageable;

public interface EventServiceV2 {
    EventListResponseDto getEventsList(long clubId, Pageable pageable);

    EventResponseDto getEvent(long eventId);

    EventResponseDto createEvent(long clubId, EventDto eventDto);

    EventResponseDto updateEvent(long eventId, EventDto eventDto);

    void cancelEvent(long eventId);

    ConfirmationResponse addConfirmation(long eventId, String userUid);

    void deleteConfirmation(long eventId, String userUid);

    EventListResponseDto getEventsOwnByUser(long clubId, String userUid, Pageable pageable);

    EventListResponseDto getEventsConfirmedByUser(long clubId, String userUid, Pageable pageable);
}
