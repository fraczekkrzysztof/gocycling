package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ConversationRepositorySearch {

    Page<Conversation> findByEventId(@Param("eventId") long eventId, Pageable pageable);
}
