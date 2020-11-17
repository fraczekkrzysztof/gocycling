package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.ConversationRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.event.ConversationDto;
import com.fraczekkrzysztof.gocycling.entity.Conversation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.mapper.event.ConversationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ConversationServiceV2Impl implements ConversationServiceV2 {

    private final ConversationRepository conversationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ConversationMapper conversationMapper;

    @Override
    public List<ConversationDto> getAllByEventId(long eventId) {
        return conversationMapper.mapConversationListToConversationDtoList(conversationRepository.findByEventId(eventId));
    }

    @Override
    public ConversationDto addConversation(long eventId, ConversationDto conversationDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no event of id %d", eventId)));
        User user = userRepository.findById(conversationDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no user of id %s", conversationDto.getUserId())));
        conversationDto.setId(0L);
        conversationDto.setCreated(LocalDateTime.now());
        Conversation conversation = conversationMapper.mapConversationDtoToConversation(conversationDto, event, user);
        conversationRepository.save(conversation);
        return conversationMapper.mapConversationToConversationDto(conversation);
    }


}
