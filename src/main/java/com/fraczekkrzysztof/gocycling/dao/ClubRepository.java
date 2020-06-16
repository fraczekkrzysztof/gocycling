package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@RepositoryRestResource(path = "clubs")
@Transactional
public interface ClubRepository extends JpaRepository<Club,Long> {

    @Query("select c from Member m join m.club c where m.userUid = :userUid")
    Page<Club> findAllWhichUserIsMember(@Param("userUid") String userUid, Pageable page);

    @Query("select c from Event e join e.club c where e.id = :eventId")
    Page<Club> findClubForEventId(@Param("eventId") long eventId, Pageable page);
}
