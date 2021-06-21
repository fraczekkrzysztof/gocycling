package com.fraczekkrzysztof.gocycling.mapper.route;

import com.fraczekkrzysztof.gocycling.dto.route.RouteDto;
import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import com.fraczekkrzysztof.gocycling.external.strava.model.StravaRouteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteMapper {

    public List<RouteDto> mapStravaListToRouteDtoList(List<StravaRouteDto> stravaRoutes) {
        return stravaRoutes.stream()
                .map(this::mapSingleStracaRouteToRouteDto)
                .collect(Collectors.toList());
    }

    private RouteDto mapSingleStracaRouteToRouteDto(StravaRouteDto stravaRoute) {
        Double doubleLength = stravaRoute.getDistance() / 1000;
        return RouteDto.builder()
                .appType(ExternalApps.STRAVA)
                .link("https://www.strava.com/routes/" + stravaRoute.getId())
                .name(stravaRoute.getName())
                .length(doubleLength.intValue() + "km")
                .elevation(stravaRoute.getElevationGain().intValue() + "m")
                .created(stravaRoute.getTimestamp()).build();
    }
}
