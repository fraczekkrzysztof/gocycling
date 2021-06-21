package com.fraczekkrzysztof.gocycling.dto.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventResponseDto {

    EventDto event;
}
