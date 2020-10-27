package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.club.ClubDto;

import java.util.List;

public interface ClubServiceV2 {

    List<ClubDto> getAllClubs();

    ClubDto getClubById(long id);

    ClubDto addClub(ClubDto clubDto);
}
