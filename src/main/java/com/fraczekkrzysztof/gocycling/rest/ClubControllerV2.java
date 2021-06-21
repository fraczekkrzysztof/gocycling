package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.dto.club.ClubDto;
import com.fraczekkrzysztof.gocycling.dto.club.ClubListResponse;
import com.fraczekkrzysztof.gocycling.dto.club.ClubResponse;
import com.fraczekkrzysztof.gocycling.dto.club.MemberResponse;
import com.fraczekkrzysztof.gocycling.service.ClubServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/")
@RequiredArgsConstructor
public class ClubControllerV2 {

    public final ClubServiceV2 clubService;

    @GetMapping(path = "clubs", params = {"!userUid"})
    public ResponseEntity<ClubListResponse> getAllClubs(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clubService.getAllClubs(pageable));
    }

    @GetMapping(path = "clubs", params = {"userUid"})
    public ResponseEntity<ClubListResponse> getClubByUserMembership(@RequestParam("userUid") String userUid, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clubService.getClubByUserMembership(userUid, pageable));
    }

    @GetMapping("clubs/{id}")
    public ResponseEntity<ClubResponse> getClubById(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clubService.getClubById(id));
    }

    @PostMapping("clubs")
    public ResponseEntity<ClubResponse> addClub(@RequestBody ClubDto clubDto) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(clubService.addClub(clubDto));
    }

    @PostMapping("clubs/{id}/members")
    public ResponseEntity<MemberResponse> addMembership(@PathVariable("id") long clubId, @RequestParam("userUid") String userUid) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clubService.addMembership(clubId, userUid));
    }

    @DeleteMapping("clubs/{id}/members")
    public ResponseEntity<String> removeMembership(@PathVariable("id") long clubId, @RequestParam("userUid") String userUid) {
        clubService.deleteMembership(clubId, userUid);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
