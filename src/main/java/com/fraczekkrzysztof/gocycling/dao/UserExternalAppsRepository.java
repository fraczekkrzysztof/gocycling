package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserExternalAppsRepository extends JpaRepository<UserExternalApp,Long>, UserExternalAppsRepositorySearch {

    @Override
    @Query("select uea from UserExternalApp uea join uea.user u where u.id = :userUid and uea.appType='STRAVA'")
    Optional<UserExternalApp> findStravaByUserUid(@Param("userUid") String userUid);

    @Override
    @Query("select uea from UserExternalApp uea where uea.user.id = :userUid")
    List<UserExternalApp> findExternalAppByUserUid(@Param("userUid")String userUid);
}
