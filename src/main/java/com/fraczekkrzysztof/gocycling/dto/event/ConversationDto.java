package com.fraczekkrzysztof.gocycling.dto.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConversationDto {

    private long id;
    private String userId;
    private String userName;
    private LocalDateTime created;
    private String message;
}
