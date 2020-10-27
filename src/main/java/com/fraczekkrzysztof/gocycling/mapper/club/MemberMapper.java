package com.fraczekkrzysztof.gocycling.mapper.club;

import com.fraczekkrzysztof.gocycling.dto.club.MemberDto;
import com.fraczekkrzysztof.gocycling.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberMapper {

    public List<MemberDto> mapMemberEntityListToMemberDtoList(List<Member> memberList) {
        return memberList.stream().map(this::mapMemberEntityToMemberDto).collect(Collectors.toList());
    }

    private MemberDto mapMemberEntityToMemberDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .userUid(member.getUserUid())
                .confirmed(member.isConfirmed())
                .build();
    }

    public List<Member> mapMemberDtoListToMemberEntityList(List<MemberDto> memberList) {
        return memberList.stream().map(this::mapMemberDtoToMemberEntity).collect(Collectors.toList());
    }

    private Member mapMemberDtoToMemberEntity(MemberDto member) {
        return Member.builder()
                .id(member.getId())
                .userUid(member.getUserUid())
                .confirmed(member.isConfirmed())
                .build();
    }
}
