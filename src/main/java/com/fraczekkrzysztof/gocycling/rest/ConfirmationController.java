package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.service.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.HttpResource;

@RestController()
public class ConfirmationController {

    ConfirmationService confirmationService;

    @Autowired
    public ConfirmationController(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @DeleteMapping("/api/custom/confirmations")
    public ResponseEntity<String> deleteByUSerUidAndEventId(@RequestParam("userUid") String userUid, @RequestParam("eventId") long eventId){
        confirmationService.deleteByUserUidAndEventId(userUid,eventId);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
