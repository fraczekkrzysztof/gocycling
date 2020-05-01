package com.fraczekkrzysztof.gocycling.external;

import com.fraczekkrzysztof.gocycling.dto.RouteDto;

import java.util.List;

public interface ExternalRoutesRetriever {
    List<RouteDto> getExternalRoutes(String userUid);
}
