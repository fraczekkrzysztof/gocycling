package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositorySearch {

    Page<User> findUserConfirmedEvent(long eventId, Pageable pageable);
}
