package com.fraczekkrzysztof.gocycling.dto.event;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventListResponseDto {

    private List<EventDto> events;
}
