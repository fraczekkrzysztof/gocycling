package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.club.ClubDto;
import com.fraczekkrzysztof.gocycling.dto.club.ClubListResponse;
import com.fraczekkrzysztof.gocycling.dto.club.MemberDto;
import org.springframework.data.domain.Pageable;

public interface ClubServiceV2 {

    ClubListResponse getAllClubs(Pageable pageable);

    ClubDto getClubById(long id);

    ClubDto addClub(ClubDto clubDto);

    MemberDto addMembership(long clubId, String userUid);

    void deleteMembership(long clubId, String userUid);

    ClubListResponse getClubByUserMembership(String userUid, Pageable pageable);
}
