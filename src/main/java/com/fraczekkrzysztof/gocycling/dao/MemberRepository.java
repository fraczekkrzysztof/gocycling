package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@RepositoryRestResource(path = "members")
@Transactional
public interface MemberRepository extends JpaRepository<Member,Long> {

    @Query("select m from Member m join m.club c where c.id = :clubId and m.userUid = :userUid")
    Page<Member> findAllByUserUidAndClubId(String userUid, long clubId, Pageable pageable);
}
