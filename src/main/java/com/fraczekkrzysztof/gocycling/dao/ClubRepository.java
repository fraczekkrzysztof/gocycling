package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query("select c from Club c join c.memberList m where m.user.id = :userUid")
    Page<Club> findAllClubsWithUserMembership(@Param("userUid") String userUid, Pageable pageable);
}
