package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("select c from Conversation c where c.event.id = :eventId")
    Page<Conversation> findByEventId(@Param("eventId") long eventId, Pageable pageable);
}
