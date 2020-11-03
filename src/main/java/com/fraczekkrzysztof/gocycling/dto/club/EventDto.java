package com.fraczekkrzysztof.gocycling.dto.club;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventDto {

    private long id;
    private String name;
    private String place;
    private double latitude;
    private double longitude;
    private LocalDateTime dateAndTime;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String details;
    private String userId;
    private String userName;
    private boolean canceled;
    private String routeLink;
    private List<ConfirmationDto> confirmationList;
    private long clubId;
    private long clubName;
}

