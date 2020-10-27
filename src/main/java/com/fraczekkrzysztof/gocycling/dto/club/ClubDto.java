package com.fraczekkrzysztof.gocycling.dto.club;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fraczekkrzysztof.gocycling.entity.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubDto {

    long id;
    String name;
    String location;
    private double latitude;
    private double longitude;
    private String owner;
    private LocalDateTime created;
    private String details;
    private boolean privateMode;
    private List<Event> eventList;
    private List<MemberDto> memberList;

}
