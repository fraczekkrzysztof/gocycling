package com.fraczekkrzysztof.gocycling.dto.route;

import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import com.fraczekkrzysztof.gocycling.external.strava.model.StravaRouteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteDto {

    private ExternalApps appType;
    private String link;
    private String name;
    private String length;
    private String elevation;
    private long created;

    public static List<RouteDto> parseStravaApiResponse(List<StravaRouteDto> stravaRoutes){
        List<RouteDto> toReturnList = new ArrayList<>();
        for (StravaRouteDto stravaRoute : stravaRoutes){
            Double doubleLength = stravaRoute.getDistance()/1000;
            RouteDto singleRoute = RouteDto.builder()
                    .appType(ExternalApps.STRAVA)
                    .link("https://www.strava.com/routes/"+ stravaRoute.getId())
                    .name(stravaRoute.getName())
                    .length(doubleLength.intValue() + "km")
                    .elevation(stravaRoute.getElevationGain().intValue() + "m")
                    .created(stravaRoute.getTimestamp()).build();
            toReturnList.add(singleRoute);
        }
        return toReturnList;
    }
}
