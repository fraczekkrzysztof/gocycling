package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.event.ConversationDto;

import java.util.List;

public interface ConversationServiceV2 {
    List<ConversationDto> getAllByEventId(long eventId);

    ConversationDto addConversation(long eventId, ConversationDto conversationDto);
}
