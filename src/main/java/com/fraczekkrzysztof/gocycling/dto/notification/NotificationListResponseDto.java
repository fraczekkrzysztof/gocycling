package com.fraczekkrzysztof.gocycling.dto.notification;


import com.fraczekkrzysztof.gocycling.paging.PageDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationListResponseDto {

    private List<NotificationDto> notifications;
    private PageDto page;
}
