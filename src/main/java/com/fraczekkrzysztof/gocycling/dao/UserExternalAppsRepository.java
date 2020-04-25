package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserExternalAppsRepository extends JpaRepository<UserExternalApp,Long>, UserExternalAppsRepositorySearch {

    @Override
    @Query("select uea from UserExternalApp uea join uea.user u where u.id = :userUid and uea.appType='STRAVA'")
    Optional<UserExternalApp> findStravaByUserUid(String userUid);
}
