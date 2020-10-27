package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.dto.club.ClubDto;
import com.fraczekkrzysztof.gocycling.dto.club.ClubListResponse;
import com.fraczekkrzysztof.gocycling.dto.club.ClubResponse;
import com.fraczekkrzysztof.gocycling.service.ClubServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/clubs")
@RequiredArgsConstructor
public class ClubRepositoryV2 {

    public final ClubServiceV2 clubService;

    @GetMapping
    public ResponseEntity<ClubListResponse> getAllClubs() {
        return ResponseEntity.status(HttpStatus.OK).body(ClubListResponse.builder().clubs(clubService.getAllClubs()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubResponse> getClubById(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ClubResponse.builder().club(clubService.getClubById(id)).build());
    }

    @PostMapping
    public ResponseEntity<ClubResponse> addClub(@RequestBody ClubDto clubDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ClubResponse.builder().club(clubService.addClub(clubDto)).build());
    }

}
