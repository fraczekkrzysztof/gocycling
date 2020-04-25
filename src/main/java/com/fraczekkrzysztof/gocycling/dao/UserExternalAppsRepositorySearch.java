package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;

import java.util.Optional;

public interface UserExternalAppsRepositorySearch {

    Optional<UserExternalApp> findStravaByUserUid(String userUid);
}
