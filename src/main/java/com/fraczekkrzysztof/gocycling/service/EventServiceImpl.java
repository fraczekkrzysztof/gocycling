package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.entity.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public void cancelEvent(long eventId) throws Exception {
        Event eventToCancel = eventRepository.findById(eventId).orElseThrow(() -> new Exception("There is no event to cancel. Event id: " + eventId));
        eventToCancel.setCanceled(true);
        eventRepository.save(eventToCancel);
    }
}
