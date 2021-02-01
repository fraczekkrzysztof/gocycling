package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.dto.event.ConfirmationResponse;
import com.fraczekkrzysztof.gocycling.dto.event.EventDto;
import com.fraczekkrzysztof.gocycling.dto.event.EventListResponseDto;
import com.fraczekkrzysztof.gocycling.dto.event.EventResponseDto;
import com.fraczekkrzysztof.gocycling.service.EventServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/clubs/{id}")
@RequiredArgsConstructor
public class EventControllerV2 {

    private final EventServiceV2 eventService;

    @GetMapping("/events")
    public ResponseEntity<EventListResponseDto> getClubsEventList(@PathVariable("id") long clubId, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(eventService.getEventsList(clubId, pageable));
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable("eventId") long eventId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(eventService.getEvent(eventId));
    }

    @PostMapping("/events")
    public ResponseEntity<EventResponseDto> createEvent(@PathVariable("id") long clubId, @RequestBody EventDto eventDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventService.createEvent(clubId, eventDto));
    }

    @PutMapping("/events/{eventId}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable("eventId") long eventId, @RequestBody EventDto eventDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(eventService.updateEvent(eventId, eventDto));
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
                .body(eventService.addConfirmation(eventId, userUid));
    }

    @DeleteMapping("/events/{eventId}/confirmations")
    public ResponseEntity<String> deleteConfirmation(@PathVariable("eventId") long eventId, @RequestParam("userUid") String userUid) {
        eventService.deleteConfirmation(eventId, userUid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @GetMapping("/events/createdBy")
    public ResponseEntity<EventListResponseDto> getEventsCreatedByUser(@PathVariable("id") long clubId, @RequestParam("userUid") String userUid, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(eventService.getEventsOwnByUser(clubId, userUid, pageable));
    }

    @GetMapping("/events/confirmedBy")
    public ResponseEntity<EventListResponseDto> getEventsConfirmedByUser(@PathVariable("id") long clubId, @RequestParam("userUid") String userUid, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(eventService.getEventsConfirmedByUser(clubId, userUid, pageable));
    }
}
