package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.ConfirmationRepository;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationServiceImpl implements ConfirmationService{

    private final ConfirmationRepository confirmationRepository;


    @Override
    public void deleteByUserUidAndEventId(String userUid, long eventId) {
        Confirmation confirmationToDelete = confirmationRepository.findByUserUidAndEventId(userUid,eventId, PageRequest.of(0,5)).getContent().get(0);
        if (confirmationToDelete != null){
            confirmationRepository.delete(confirmationToDelete);
        }

    }
}
