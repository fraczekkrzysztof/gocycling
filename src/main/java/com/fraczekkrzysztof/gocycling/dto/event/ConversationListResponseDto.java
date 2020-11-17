package com.fraczekkrzysztof.gocycling.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class ConversationListResponseDto {

    List<ConversationDto> conversations;
}
