package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e join e.club c where c.id = :id")
    List<Event> findByClubId(@Param("id") long clubId);
//    //this endpoint is available for /events/search/findCurrent
//    @Query(value = "select e from Event e where e.dateAndTime > current_timestamp() and canceled=false order by e.dateAndTime")
//    Page<Event> findCurrent(Pageable pageable);
//    //this endpoint is available for events/search/findByName?name=testName
//    @Query(value = "select e from Event e where e.name = :name")
//    Page<Event> findByName(@Param("name") String name, Pageable pageable);
//
//    @Query(value = "select e from Confirmation as c join c.event as e where e.dateAndTime > current_timestamp()" +
//            "and c.userUid=:userUid order by e.dateAndTime")
//    Page<Event> findConfirmedByUserUid(@Param("userUid") String userUid, Pageable pageable);
//
//    @Query(value = "select e from Event e where e.dateAndTime > current_timestamp() and e.createdBy = :userUid order by e.dateAndTime")
//    Page<Event> findByUserUid(@Param("userUid")String userUid, Pageable pageable);
//
//    @Query(value = "select e from Event e join e.club c where c.id=:clubId and e.dateAndTime > current_timestamp() and e.createdBy = :userUid order by e.dateAndTime")
//    Page<Event> findByUserUidAndClubId(@Param("userUid") String userUid, @Param("clubId") long clubId, Pageable pageable);
//
//    @Query (value = "select e from Notification n join n.event e where n.id = :notificationId")
//    Page<Event> findEventByNotificationId(@Param("notificationId") long notificationId, Pageable pageable);
//
//    @Query(value = "select e from Event e join e.club c where c.id = :clubId and e.dateAndTime > current_timestamp() and e.canceled=false order by e.dateAndTime")
//    Page<Event> findCurrentByClubId(@Param("clubId") long clubId, Pageable pageable);

}
