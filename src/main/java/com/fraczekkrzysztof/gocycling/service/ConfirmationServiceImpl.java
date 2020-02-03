package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.ConfirmationRepository;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationServiceImpl implements ConfirmationService{

    ConfirmationRepository confirmationRepository;

    @Autowired
    public ConfirmationServiceImpl(ConfirmationRepository confirmationRepository) {
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public void deleteByUserUidAndEventId(String userUid, long eventId) {
        Confirmation confirmationToDelete = confirmationRepository.findByUserUidAndEventId(userUid,eventId, PageRequest.of(0,5)).getContent().get(0);
        if (confirmationToDelete != null){
            confirmationRepository.delete(confirmationToDelete);
        }

    }
}
