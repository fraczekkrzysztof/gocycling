package com.fraczekkrzysztof.gocycling.dao;


import com.fraczekkrzysztof.gocycling.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@RepositoryRestResource(path = "users")
@Transactional
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u join Confirmation c on (c.userUid=u.id) where c.event.id = :eventId")
    Page<User> findUserConfirmedEvent(@Param("eventId") long eventId, Pageable pageable);

//    @Override
//    @Query("select u from User u join Member m on (m.userUid=u.id) where m.club.id = :clubId")
//    Page<User> findUserClubMembers(@Param("clubId") long clubId, Pageable pageable);

}
