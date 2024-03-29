package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.dto.event.ConversationDto;
import com.fraczekkrzysztof.gocycling.dto.event.ConversationListResponseDto;
import com.fraczekkrzysztof.gocycling.dto.event.ConversationResponseDto;
import com.fraczekkrzysztof.gocycling.service.ConversationServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/clubs/{id}/events/{eventId}")
public class ConversationControllerV2 {

    private final ConversationServiceV2 conversationService;

    @GetMapping("/conversations")
    public ResponseEntity<ConversationListResponseDto> getAllEventConversations(@PathVariable("eventId") long eventId, Pageable pagebale) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(conversationService.getAllByEventId(eventId, pagebale));
    }

    @PostMapping("conversations")
    public ResponseEntity<ConversationResponseDto> addConversation(@PathVariable("eventId") long eventId, @RequestBody ConversationDto conversationDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(conversationService.addConversation(eventId, conversationDto));
    }
}
