package com.fraczekkrzysztof.gocycling.external.strava;

import com.fraczekkrzysztof.gocycling.dao.UserExternalAppsRepository;
import com.fraczekkrzysztof.gocycling.dto.RouteDto;
import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import com.fraczekkrzysztof.gocycling.external.ExternalRoutesRetriever;
import com.fraczekkrzysztof.gocycling.external.strava.exception.StravaApiException;
import com.fraczekkrzysztof.gocycling.external.strava.model.StravaRouteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class StravaExternalRoutesRetriever implements ExternalRoutesRetriever {

    private final StravaProperties stravaProperties;
    private final UserExternalAppsRepository userExternalAppsRepository;
    @Qualifier("customRestTemplate")
    private final RestTemplate restTemplate;
    @Override
    public List<RouteDto> getExternalRoutes(String userUid) {
        UserExternalApp stravaAppDetails = userExternalAppsRepository.findStravaByUserUid(userUid).
                orElseThrow(() -> new NoSuchElementException("There is no strava for user " + userUid));
        StringBuilder sb = new StringBuilder();
        sb.append(stravaProperties.getApiBaseAddress());
        sb.append(stravaProperties.getApiRoutes());
        String routeRequest = String.format(sb.toString(),stravaAppDetails.getAppUserId());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(stravaAppDetails.getAccessToken());
        ResponseEntity<List<StravaRouteDto>> listOfStravaRoutesResponseEntity = restTemplate.
                exchange(routeRequest, HttpMethod.GET,new HttpEntity<>(null,headers),new ParameterizedTypeReference<List<StravaRouteDto>>() {
        });
        if (!listOfStravaRoutesResponseEntity.getStatusCode().is2xxSuccessful() ){
            throw new StravaApiException("Error during retrieving list of routes");
        }

        return RouteDto.parseStravaApiResponse(listOfStravaRoutesResponseEntity.getBody());
    }
}
