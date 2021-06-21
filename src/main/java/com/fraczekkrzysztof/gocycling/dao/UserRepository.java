package com.fraczekkrzysztof.gocycling.dao;


import com.fraczekkrzysztof.gocycling.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("Select u from User u join fetch u.externalAppList ea where ea.appType='STRAVA'")
    List<User> findAllWithStravaConnected();
}
