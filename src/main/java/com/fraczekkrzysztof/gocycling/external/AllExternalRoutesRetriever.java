package com.fraczekkrzysztof.gocycling.external;

import com.fraczekkrzysztof.gocycling.dto.route.RouteDto;

import java.util.List;

public interface AllExternalRoutesRetriever {

    List<RouteDto> getExternalRoutes(String userUid);
}
