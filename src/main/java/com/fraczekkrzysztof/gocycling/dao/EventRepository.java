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
    @Query(nativeQuery = true, value = "select * from public.event where ev_date_and_time>now()", countQuery = "select count(*) from public.event where ev_date_and_time>now()")
    Page<Event> findCurrent(Pageable pageable);
    //this endpoint is available for events/search/findByName?name=testName
    @Override
    @Query(value = "select e from Event e where e.name = :name")
    Page<Event> findByName(@Param("name") String name, Pageable pageable);

    @Override
    @Query(nativeQuery = true , value = "select * from Event e where e.ev_date_and_time> now() and exists (select 1 from Confirmation c where c.con_ev_id = e.ev_id and c.con_user_uid = :userUid)"
            ,countQuery = "select count(*) from Event e where e.ev_date_and_time> now() and exists (select 1 from Confirmation c where c.con_ev_id = e.ev_id and c.con_user_uid = :userUid)")
    Page<Event> findConfirmedByUserUid(String userUid, Pageable pageable);
}
