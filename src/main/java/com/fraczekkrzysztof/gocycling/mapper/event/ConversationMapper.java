package com.fraczekkrzysztof.gocycling.mapper.event;

import com.fraczekkrzysztof.gocycling.dto.event.ConversationDto;
import com.fraczekkrzysztof.gocycling.entity.Conversation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationMapper {

    public List<ConversationDto> mapConversationListToConversationDtoList(List<Conversation> conversationList) {
        return conversationList.stream().map(this::mapConversationToConversationDto).collect(Collectors.toList());
    }

    public ConversationDto mapConversationToConversationDto(Conversation c) {
        return ConversationDto.builder()
                .id(c.getId())
                .created(c.getCreated())
                .message(c.getMessage())
                .userId(c.getUser().getId())
                .userName(c.getUser().getName())
                .build();
    }

    public Conversation mapConversationDtoToConversation(ConversationDto conversation, Event event, User user) {
        return Conversation.builder()
                .id(conversation.getId())
                .created(conversation.getCreated())
                .message(conversation.getMessage())
                .event(event)
                .user(user)
                .build();
    }
}
