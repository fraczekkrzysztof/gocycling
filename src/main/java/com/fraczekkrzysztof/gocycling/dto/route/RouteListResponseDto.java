package com.fraczekkrzysztof.gocycling.dto.route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RouteListResponseDto {

    private List<RouteDto> routes;
}
