package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.ClubRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.club.ClubDto;
import com.fraczekkrzysztof.gocycling.dto.club.MemberDto;
import com.fraczekkrzysztof.gocycling.entity.Club;
import com.fraczekkrzysztof.gocycling.entity.Member;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.mapper.club.ClubMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubServiceV2Impl implements ClubServiceV2 {

    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;
    private final UserRepository userRepository;

    public List<ClubDto> getAllClubs() {
        return clubMapper.mapClubEntityListToClubDtoList(clubRepository.findAll(), false, false);
    }

    public ClubDto getClubById(long id) {
        ClubDto mappedClub = clubMapper
                .mapClubEntityToClubDto(clubRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("There is no club of id %d", id))), false, true);
        List<String> listOfUsersId = mappedClub.getMemberList().stream().map(m -> m.getUserUid()).collect(Collectors.toList());
        List<User> usersList = userRepository.findByIdIn(listOfUsersId);
        return clubMapper.addUserNameInformationToClubDto(mappedClub, usersList);
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

    @Override
    public MemberDto addMembership(long clubId, String userUid) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new NoSuchElementException(String.format("There is no club od id %d", clubId)));
        Member newMember = Member.builder()
                .userUid(userUid)
                .confirmed(true)
                .build();
        club.getMemberList().add(newMember);
        clubRepository.save(club);
        //get an id of new element
        long newMemberId = club.getMemberList().stream().filter(m -> m.getUserUid() == userUid).map(m -> m.getId()).findFirst().orElse(-1L);
        return MemberDto.builder()
                .id(newMemberId)
                .userUid(newMember.getUserUid())
                .confirmed(newMember.isConfirmed())
                .build();
    }

    @Override
    public void deleteMembership(long clubId, String userUid) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new NoSuchElementException(String.format("There is no club od id %d", clubId)));
        Member memberToRemove = club.getMemberList()
                .stream()
                .filter(m -> m.getUserUid().equals(userUid))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("User is not e member of a club", clubId)));
        club.getMemberList().remove(memberToRemove);
        clubRepository.save(club);
    }

    @Override
    public List<ClubDto> getClubByUSerMembership(String userUid) {
        List<Club> clubList = clubRepository.findAllClubsWithUserMembership(userUid);
        return clubMapper.mapClubEntityListToClubDtoList(clubList, false, false);
    }


}
