package com.fraczekkrzysztof.gocycling.dto.club;

import com.fraczekkrzysztof.gocycling.entity.User;
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
    private User userId;
    private User userName;
    private LocalDateTime created;
    private String message;
}
