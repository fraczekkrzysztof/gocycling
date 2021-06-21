package com.fraczekkrzysztof.gocycling.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ConfirmationResponse {

    private ConfirmationDto confirmation;
}
