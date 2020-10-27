package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.ClubRepository;
import com.fraczekkrzysztof.gocycling.dto.club.ClubDto;
import com.fraczekkrzysztof.gocycling.entity.Club;
import com.fraczekkrzysztof.gocycling.mapper.club.ClubMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ClubServiceV2Impl implements ClubServiceV2 {

    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;

    public List<ClubDto> getAllClubs() {
        return clubMapper.mapClubEntityListToClubDtoList(clubRepository.findAll(), false, false);
    }

    public ClubDto getClubById(long id) {
        return clubMapper
                .mapClubEntityToClubDto(clubRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("There is no club of id %d", id))), false, true);
    }


    @Override
    public ClubDto addClub(ClubDto clubDto) {
        clubDto.setId(0L);
        clubDto.setCreated(LocalDateTime.now());
        Club club = clubMapper.mapClubDtoToClubEntity(clubDto);
        clubRepository.save(club);
        clubDto.setId(club.getId());
        return clubDto;
    }
}
