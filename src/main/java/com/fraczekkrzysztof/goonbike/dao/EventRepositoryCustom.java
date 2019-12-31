package com.fraczekkrzysztof.goonbike.dao;

import com.fraczekkrzysztof.goonbike.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepositoryCustom {
    Page<Event> findCurrent(Pageable pageable);
    Page<Event> findByName(String name, Pageable pageable);
}
