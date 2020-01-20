package com.fraczekkrzysztof.gocycling.service;

public interface ConfirmationService {

    void deleteByUserUidAndEventId(String userUid, long eventId);
}
