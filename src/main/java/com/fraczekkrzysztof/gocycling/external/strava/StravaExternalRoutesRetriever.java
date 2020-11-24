package com.fraczekkrzysztof.gocycling.external.strava;

import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.route.RouteDto;
import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
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

    private final UserRepository userRepository;
    private final StravaProperties stravaProperties;
    @Qualifier("customRestTemplate")
    private final RestTemplate restTemplate;
    @Override
    public List<RouteDto> getExternalRoutes(String userUid) {
        UserExternalApp stravaAppDetails = userRepository.findById(userUid)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no user of id %s ", userUid)))
                .getExternalAppList().stream()
                .filter(uea -> ExternalApps.STRAVA.equals(uea.getAppType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("User %s doesn't have strava connected", userUid)));
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
