package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.transaction.Transactional;
import java.util.List;

@RepositoryRestResource(path = "confirmations")
@Transactional
public interface ConfirmationRepository extends JpaRepository<Confirmation,Long>, ConfirmationRepositorySearch {
    //if you want to pass existing event then pas as "event":"/api/events/{id}"

    Page<Confirmation> findByUserUid(@Param("userUid") String userUid, Pageable pageable);

    @Query("select c from Confirmation c where c.userUid = :userUid and c.event.id=:id")
    Page<Confirmation> findByUserUidAndEventId(@Param("userUid") String userUid, @Param("id") long eventId, Pageable pageable);

    @RestResource(exported = false)
    @Query("select c from Confirmation c join c.event e where e.id = :id")
    List<Confirmation> findByEventId(@Param("id")long eventId);
}
