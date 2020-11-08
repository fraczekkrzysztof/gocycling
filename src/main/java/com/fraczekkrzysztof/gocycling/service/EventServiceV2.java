package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.club.EventDto;

import java.util.List;

public interface EventServiceV2 {
    List<EventDto> getEventsList(long clubId);

    EventDto getEvent(long eventId);

    EventDto createEvent(long clubId, EventDto eventDto);
}
