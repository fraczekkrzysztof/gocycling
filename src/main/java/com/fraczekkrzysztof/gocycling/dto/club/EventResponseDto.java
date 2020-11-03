package com.fraczekkrzysztof.gocycling.dto.club;

import com.fraczekkrzysztof.gocycling.entity.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventResponseDto {

    Event event;
}
