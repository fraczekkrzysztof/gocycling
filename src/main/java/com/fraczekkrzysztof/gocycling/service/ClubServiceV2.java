package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.club.ClubDto;
import com.fraczekkrzysztof.gocycling.dto.club.ClubListResponse;
import com.fraczekkrzysztof.gocycling.dto.club.ClubResponse;
import com.fraczekkrzysztof.gocycling.dto.club.MemberResponse;
import org.springframework.data.domain.Pageable;

public interface ClubServiceV2 {

    ClubListResponse getAllClubs(Pageable pageable);

    ClubResponse getClubById(long id);

    ClubResponse addClub(ClubDto clubDto);

    MemberResponse addMembership(long clubId, String userUid);

    void deleteMembership(long clubId, String userUid);

    ClubListResponse getClubByUserMembership(String userUid, Pageable pageable);
}
