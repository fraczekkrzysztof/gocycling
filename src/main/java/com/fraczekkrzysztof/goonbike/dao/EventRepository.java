package com.fraczekkrzysztof.goonbike.dao;

import com.fraczekkrzysztof.goonbike.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;



@Transactional
public interface EventRepository extends CrudRepository<Event, Long>, EventRepositoryCustom {
    //this endpoint is available for /events/search/findCurrent
    @Override
    @Query(nativeQuery = true, value = "select * from public.event where date_and_time>now()", countQuery = "select count(*) from public.event where date_and_time>now()")
    Page<Event> findCurrent(Pageable pageable);
    //this endpoint is available for events/search/findByName?name=testName
    @Override
    @Query(value = "select e from Event e where e.name = :name")
    Page<Event> findByName(@Param("name") String name, Pageable pageable);

}
