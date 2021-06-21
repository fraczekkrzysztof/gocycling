package com.fraczekkrzysztof.gocycling.external.strava;

import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.route.RouteDto;
import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import com.fraczekkrzysztof.gocycling.external.ExternalRoutesRetriever;
import com.fraczekkrzysztof.gocycling.external.strava.exception.StravaApiException;
import com.fraczekkrzysztof.gocycling.external.strava.model.StravaRouteDto;
import com.fraczekkrzysztof.gocycling.mapper.route.RouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class StravaExternalRoutesRetriever implements ExternalRoutesRetriever {

    private final UserRepository userRepository;
    private final StravaProperties stravaProperties;
    private final StravaOAuthAuthorizer stravaOAuthAuthorizer;
    private final RouteMapper routeMapper;
    @Qualifier("customRestTemplate")
    private final RestTemplate restTemplate;
    @Override
    public List<RouteDto> getExternalRoutes(String userUid) {
        stravaOAuthAuthorizer.refreshToken(userUid);
        UserExternalApp stravaAppDetails = userRepository.findById(userUid)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no user of id %s ", userUid)))
                .getExternalAppList().stream()
                .filter(uea -> ExternalApps.STRAVA.equals(uea.getAppType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("User %s doesn't have strava connected", userUid)));
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("stravaUserId", String.valueOf(stravaAppDetails.getAppUserId()));
        UriComponentsBuilder routeRequest = UriComponentsBuilder
                .fromUriString(stravaProperties.getApiBaseAddress() + stravaProperties.getApiRoutes())
                .uriVariables(variables);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(stravaAppDetails.getAccessToken());
        ResponseEntity<List<StravaRouteDto>> listOfStravaRoutesResponseEntity = restTemplate.
                exchange(routeRequest.toUriString(), HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<StravaRouteDto>>() {
        });
        if (!listOfStravaRoutesResponseEntity.getStatusCode().is2xxSuccessful() ){
            throw new StravaApiException("Error during retrieving list of routes");
        }

        return routeMapper.mapStravaListToRouteDtoList(listOfStravaRoutesResponseEntity.getBody());
    }
}
