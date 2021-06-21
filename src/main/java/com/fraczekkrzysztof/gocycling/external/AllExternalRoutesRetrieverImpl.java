package com.fraczekkrzysztof.gocycling.external;

import com.fraczekkrzysztof.gocycling.dto.route.RouteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllExternalRoutesRetrieverImpl implements AllExternalRoutesRetriever {

    @Qualifier("stravaExternalRoutesRetriever")
    private final ExternalRoutesRetriever stravaRoutes;

    @Override
    public List<RouteDto> getExternalRoutes(String userUid) {
        return stravaRoutes.getExternalRoutes(userUid).stream().sorted(Comparator.comparingLong(RouteDto::getCreated).reversed()).collect(Collectors.toList());
    }
}
