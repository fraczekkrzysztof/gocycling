package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventRepositorySearch {
    Page<Event> findCurrent(Pageable pageable);
    Page<Event> findByName(String name, Pageable pageable);
    Page<Event> findConfirmedByUserUid(String userUid, Pageable pageable);
    Page<Event> findByUserUid(String userUid, Pageable pageable);
    Page<Event> findEventByNotificationId(long notificationId, Pageable pageable);
}
