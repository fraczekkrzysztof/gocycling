package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query("select c from Club c join c.memberList m where m.userUid = :userUid")
    List<Club> findAllClubsWithUserMembership(@Param("userUid") String userUid);
}
