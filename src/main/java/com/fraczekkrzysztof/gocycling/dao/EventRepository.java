package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;


public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e join e.club c where c.id = :id and e.dateAndTime >= :dateTime and e.canceled = false")
    Page<Event> findInFutureByClubId(@Param("id") long clubId, @Param("dateTime") LocalDateTime dateTime, Pageable pageable);

    @Query("select e from Event e join e.user u join e.club c where c.id = :id and u.id = :userUid and e.dateAndTime >= :dateTime")
    Page<Event> findByClubIdAndOwner(@Param("id") long clubId, @Param("userUid") String userUid, @Param("dateTime") LocalDateTime dateTime, Pageable pageable);

    @Query("select e from Event e join e.club c join e.confirmationList cl join cl.user u where c.id = :id and u.id = :userUid and e.dateAndTime >= :dateTime and e.canceled = false")
    Page<Event> findByClubIdAndUserConfirmation(@Param("id") long clubId, @Param("userUid") String userUid, @Param("dateTime") LocalDateTime dateTime, Pageable pageable);

    @Query("select e from Event e where e.dateAndTime < :dateTime")
    Page<Event> findEventsOlderThan(@Param("dateTime") LocalDateTime dateTime, Pageable pageable);
}
