package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.ClubRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.club.*;
import com.fraczekkrzysztof.gocycling.entity.Club;
import com.fraczekkrzysztof.gocycling.entity.Member;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.mapper.club.ClubMapper;
import com.fraczekkrzysztof.gocycling.paging.PageDto;
import com.fraczekkrzysztof.gocycling.paging.PagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubServiceV2Impl implements ClubServiceV2 {

    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;
    private final UserRepository userRepository;
    private final PagingService pagingService;

    public ClubListResponse getAllClubs(Pageable pageable) {
        Page<Club> pagedClubs = clubRepository.findAll(pageable);
        PageDto pageDto = pagingService.generatePageInfo(pagedClubs);
        List<ClubDto> mappedClubList = clubMapper.mapClubEntityListToClubDtoList(pagedClubs.getContent(), false, false);
        return ClubListResponse.builder().clubs(mappedClubList).page(pageDto).build();
    }

    public ClubResponse getClubById(long id) {
        ClubDto club = clubMapper
                .mapClubEntityToClubDto(clubRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("There is no club of id %d", id))), false, true);
        return ClubResponse.builder().club(club).build();
    }

    @Override
    public ClubResponse addClub(ClubDto clubDto) {
        clubDto.setId(0L);
        clubDto.setCreated(LocalDateTime.now());
        User owner = userRepository.findById(clubDto.getOwnerId()).
                orElseThrow(() -> new NoSuchElementException(String.format("There is no user of id %s", clubDto.getOwnerId())));
        Club club = clubMapper.mapClubDtoToClubEntity(clubDto, owner);
        clubRepository.save(club);
        ClubDto mappedAddedClub = clubMapper.mapClubEntityToClubDto(club, false, false);
        return ClubResponse.builder().club(mappedAddedClub).build();
    }

    @Override
    public MemberResponse addMembership(long clubId, String userUid) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new NoSuchElementException(String.format("There is no club of id %d", clubId)));
        User user = userRepository.findById(userUid).orElseThrow(() -> new NoSuchElementException(String.format("There is no user of id %s", userUid)));
        Member newMember = Member.builder()
                .user(user)
                .confirmed(true)
                .build();
        club.getMemberList().add(newMember);
        clubRepository.save(club);
        //get an id of new element
        long newMemberId = club.getMemberList().stream().filter(m -> m.getUser() == user).map(Member::getId).findFirst().orElse(-1L);
        MemberDto mappedAddedMember = MemberDto.builder()
                .id(newMemberId)
                .userUid(user.getId())
                .userName(user.getName())
                .confirmed(newMember.isConfirmed())
                .build();
        return MemberResponse.builder().member(mappedAddedMember).build();
    }

    @Override
    public void deleteMembership(long clubId, String userUid) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new NoSuchElementException(String.format("There is no club od id %d", clubId)));
        Member memberToRemove = club.getMemberList()
                .stream()
                .filter(m -> m.getUser().getId().equals(userUid))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("User %s is not e member of a club %d", userUid, clubId)));
        club.getMemberList().remove(memberToRemove);
        clubRepository.save(club);
    }

    @Override
    public ClubListResponse getClubByUserMembership(String userUid, Pageable pageable) {
        Page<Club> pagedClub = clubRepository.findAllClubsWithUserMembership(userUid, pageable);
        PageDto pageDto = pagingService.generatePageInfo(pagedClub);
        List<ClubDto> mappedClubList = clubMapper.mapClubEntityListToClubDtoList(pagedClub.getContent(), false, false);
        return ClubListResponse.builder().clubs(mappedClubList).page(pageDto).build();
    }
}
