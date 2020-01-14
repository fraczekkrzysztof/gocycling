package com.fraczekkrzysztof.gocycling.dao;


import com.fraczekkrzysztof.gocycling.entity.helper.EventsConfirmedByUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.transaction.Transactional;

@RepositoryRestResource(path = "eventConfirmedByUser")
@Transactional
public interface EventConfirmedByUserRepository extends JpaRepository<EventsConfirmedByUser,Long> {

    @Query("select ecbu from EventsConfirmedByUser ecbu where ecbu.conUserUid = :userUid")
    Page<EventsConfirmedByUser> findAllUserConfirmations(@Param("userUid") String userUid, Pageable pageable);

    @Override
    @RestResource(exported = false)
    <S extends EventsConfirmedByUser> S save(S s);

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(EventsConfirmedByUser eventsConfirmedByUser);
}
