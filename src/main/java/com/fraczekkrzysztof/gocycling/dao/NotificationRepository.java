package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@RepositoryRestResource(path = "notifications")
@Transactional
public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositorySearch {

    @Override
    @Query("select n from Notification n where n.userUid = :userUid order by created desc")
    Page<Notification> findByUserUid(@Param("userUid") String userUid, Pageable pageable);
}
