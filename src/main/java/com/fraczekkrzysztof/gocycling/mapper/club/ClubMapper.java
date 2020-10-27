package com.fraczekkrzysztof.gocycling.mapper.club;

import com.fraczekkrzysztof.gocycling.dto.club.ClubDto;
import com.fraczekkrzysztof.gocycling.entity.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubMapper {

    private final MemberMapper memberMapper;

    public ClubDto mapClubEntityToClubDto(Club club, boolean getEvents, boolean getMembers) {
        ClubDto.ClubDtoBuilder builder = ClubDto.builder()
                .id(club.getId())
                .name(club.getName())
                .details(club.getDetails())
                .location(club.getLocation())
                .latitude(club.getLatitude())
                .longitude(club.getLongitude())
                .owner(club.getOwner())
                .created(club.getCreated())
                .privateMode(club.isPrivateMode());
        if (getEvents) builder.eventList(club.getEventList());
        if (getMembers) builder.memberList(memberMapper.mapMemberEntityListToMemberDtoList(club.getMemberList()));
        return builder.build();
    }

    public List<ClubDto> mapClubEntityListToClubDtoList(List<Club> clubList, boolean getEvents, boolean getMembers) {
        return clubList.stream().map(c -> mapClubEntityToClubDto(c, getEvents, getMembers)).collect(Collectors.toList());
    }

    public Club mapClubDtoToClubEntity(ClubDto club) {
        Club.ClubBuilder builder = Club.builder()
                .id(club.getId())
                .name(club.getName())
                .location(club.getLocation())
                .latitude(club.getLatitude())
                .longitude(club.getLongitude())
                .owner(club.getOwner())
                .created(club.getCreated())
                .details(club.getDetails())
                .privateMode(club.isPrivateMode())
                .eventList(null);//TODO add mapping when event mapping will be done
        if (!CollectionUtils.isEmpty(club.getMemberList())) {
            builder.memberList(memberMapper.mapMemberDtoListToMemberEntityList(club.getMemberList()));
        }
        return builder.build();
    }


}
