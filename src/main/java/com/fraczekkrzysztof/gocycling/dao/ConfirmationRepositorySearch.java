package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConfirmationRepositorySearch {

    Page<Confirmation> findByUserUid(String userUid, Pageable pageable);
    Confirmation findByUserUidAndEventId(String userUid, long eventId);
}
