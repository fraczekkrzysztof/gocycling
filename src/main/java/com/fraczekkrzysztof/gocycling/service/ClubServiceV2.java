package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.club.ClubDto;
import com.fraczekkrzysztof.gocycling.dto.club.MemberDto;

import java.util.List;

public interface ClubServiceV2 {

    List<ClubDto> getAllClubs();

    ClubDto getClubById(long id);

    ClubDto addClub(ClubDto clubDto);

    MemberDto addMembership(long clubId, String userUid);

    void deleteMembership(long clubId, String userUid);

    List<ClubDto> getClubByUSerMembership(String userUid);
}
