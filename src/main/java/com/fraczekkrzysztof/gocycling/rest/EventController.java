package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/custom/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;


    @PutMapping("/cancelEvent")
    public ResponseEntity<String> cancelEvent(@RequestParam("eventId") long eventId) throws Exception {
        eventService.cancelEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
