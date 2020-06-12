package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import javax.transaction.Transactional;

@RepositoryRestResource(path = "clubs")
@Transactional
public interface ClubRepository extends JpaRepository<Club,Long> {

    @Query("select c from Club c where c.userUid = :userUid")
    Page<Club> findAllByUserUid(@Param("userUid") String userUid);
}
