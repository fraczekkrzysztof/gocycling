package com.fraczekkrzysztof.gocycling.dto.club;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class MemberResponse {

    private MemberDto member;
}
