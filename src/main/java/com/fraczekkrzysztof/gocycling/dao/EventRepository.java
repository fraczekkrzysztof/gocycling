package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;


@RepositoryRestResource(path = "events")
@Transactional
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositorySearch {
    //this endpoint is available for /events/search/findCurrent
    @Override
    @Query(value = "select e from Event e where e.dateAndTime > current_timestamp() and canceled=false order by e.dateAndTime")
    Page<Event> findCurrent(Pageable pageable);
    //this endpoint is available for events/search/findByName?name=testName
    @Override
    @Query(value = "select e from Event e where e.name = :name")
    Page<Event> findByName(@Param("name") String name, Pageable pageable);

    @Override
    @Query(value = "select e from Confirmation as c join c.event as e where e.dateAndTime > current_timestamp()" +
            "and c.userUid=:userUid order by e.dateAndTime")
    Page<Event> findConfirmedByUserUid(@Param("userUid") String userUid, Pageable pageable);

    @Override
    @Query(value = "select e from Event e where e.dateAndTime > current_timestamp() and e.createdBy = :userUid order by e.dateAndTime")
    Page<Event> findByUserUid(@Param("userUid")String userUid, Pageable pageable);
}
