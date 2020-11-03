package com.fraczekkrzysztof.gocycling.dto.club;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfirmationDto {

    private long id;
    private String userId;
    private String userName;
}

