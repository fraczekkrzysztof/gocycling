package com.fraczekkrzysztof.gocycling.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ConversationResponseDto {

    ConversationDto conversation;
}
