package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.userUid = :userUid")
    Page<Notification> findByUserUid(@Param("userUid") String userUid, Pageable pageable);

    @Query("select n from Notification n where n.eventId in (:ids)")
    Page<Notification> findByEventIdList(@Param("ids") List<Long> ids, Pageable pageable);
}
