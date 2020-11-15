package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.dto.event.ConfirmationResponse;
import com.fraczekkrzysztof.gocycling.dto.event.EventDto;
import com.fraczekkrzysztof.gocycling.dto.event.EventListResponseDto;
import com.fraczekkrzysztof.gocycling.dto.event.EventResponseDto;
import com.fraczekkrzysztof.gocycling.service.EventServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/clubs/{id}")
@RequiredArgsConstructor
public class EventControllerV2 {

    private final EventServiceV2 eventService;

    @GetMapping("/events")
    public ResponseEntity<EventListResponseDto> getClubsEventList(@PathVariable("id") long clubId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EventListResponseDto.builder().events(eventService.getEventsList(clubId)).build());
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable("eventId") long eventId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EventResponseDto.builder().event(eventService.getEvent(eventId)).build());
    }

    @PostMapping("/events")
    public ResponseEntity<EventResponseDto> createEvent(@PathVariable("id") long clubId, @RequestBody EventDto eventDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EventResponseDto.builder().event(eventService.createEvent(clubId, eventDto)).build());
    }

    @PutMapping("/events/{eventId}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable("eventId") long eventId, @RequestBody EventDto eventDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EventResponseDto.builder().event(eventService.updateEvent(eventId, eventDto)).build());
    }

    @PatchMapping("/events/{eventId}/cancel")
    public ResponseEntity<String> cancelEvent(@PathVariable("eventId") long eventId) {
        eventService.cancelEvent(eventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @PostMapping("/events/{eventId}/confirmations")
    public ResponseEntity<ConfirmationResponse> addConfirmation(@PathVariable("eventId") long eventId, @RequestParam("userUid") String userUid) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ConfirmationResponse.builder().confirmation(eventService.addConfirmation(eventId, userUid)).build());
    }

    @DeleteMapping("/events/{eventId}/confirmations")
    public ResponseEntity<String> deleteConfirmation(@PathVariable("eventId") long eventId, @RequestParam("userUid") String userUid) {
        eventService.deleteConfirmation(eventId, userUid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("");
    }
}
