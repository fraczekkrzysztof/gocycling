package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.event.ConversationDto;
import com.fraczekkrzysztof.gocycling.dto.event.ConversationListResponseDto;
import org.springframework.data.domain.Pageable;


public interface ConversationServiceV2 {
    ConversationListResponseDto getAllByEventId(long eventId, Pageable pageable);

    ConversationDto addConversation(long eventId, ConversationDto conversationDto);
}
