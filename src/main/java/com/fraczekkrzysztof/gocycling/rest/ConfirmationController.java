package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.service.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/custom/confirmations")
@RequiredArgsConstructor
public class ConfirmationController {

    private final ConfirmationService confirmationService;


    @DeleteMapping("/deleteByUserUidAndEventId")
    public ResponseEntity<String> deleteByUSerUidAndEventId(@RequestParam("userUid") String userUid, @RequestParam("eventId") long eventId){
        confirmationService.deleteByUserUidAndEventId(userUid,eventId);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
