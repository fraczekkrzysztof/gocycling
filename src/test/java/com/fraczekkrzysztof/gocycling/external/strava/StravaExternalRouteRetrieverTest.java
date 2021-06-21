package com.fraczekkrzysztof.gocycling.external.strava;

import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.route.RouteDto;
import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import com.fraczekkrzysztof.gocycling.external.strava.model.StravaRouteDto;
import com.fraczekkrzysztof.gocycling.mapper.route.RouteMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
public class StravaExternalRouteRetrieverTest {

    @MockBean
    UserRepository userRepository;
    @MockBean
    @Qualifier("customRestTemplate")
    RestTemplate restTemplate;

    @Autowired
    RouteMapper routeMapper;
    @Autowired
    StravaProperties stravaProperties;
    @Autowired
    StravaExternalRoutesRetriever stravaRouteRetriever;
    @Autowired
    StravaOAuthAuthorizer stravaOAuthAuthorizer;

    @Test
    void shouldRetrieveOneRoute() {
        //given
        User userWithStravaConnected = mock(User.class);
        UserExternalApp stravaApp = mock(UserExternalApp.class);
        when(stravaApp.getAppType()).thenReturn(ExternalApps.STRAVA);
        when(stravaApp.getAccessToken()).thenReturn("fakeAccessToken");
        when(stravaApp.getAppUserId()).thenReturn(1000500100900L);
        when(stravaApp.getExpiresAt()).thenReturn(Instant.now().getEpochSecond() + 100 * 60);
        List<UserExternalApp> listOfExternalApps = new ArrayList<>();
        listOfExternalApps.add(stravaApp);
        when(userWithStravaConnected.getExternalAppList()).thenReturn(listOfExternalApps);
        when(userRepository.findById("userIdWithStrava")).thenReturn(Optional.of(userWithStravaConnected));

        StravaRouteDto stravaRouteDto = StravaRouteDto.builder()
                .id(1233L)
                .name("test")
                .distance(1234.45)
                .elevationGain(567.34)
                .timestamp(5454325)
                .build();
        List<StravaRouteDto> mockedRouteList = Arrays.asList(stravaRouteDto);
        HttpHeaders mockHeaders = new HttpHeaders();
        mockHeaders.setBearerAuth("fakeAccessToken");
        when(restTemplate.
                exchange("https://www.strava.com/api/v3/athletes/1000500100900/routes", HttpMethod.GET, new HttpEntity<>(null, mockHeaders), new ParameterizedTypeReference<List<StravaRouteDto>>() {
                })).thenReturn(ResponseEntity.ok(mockedRouteList));
        RouteDto expectedRoute = RouteDto.builder()
                .appType(ExternalApps.STRAVA)
                .link("https://www.strava.com/routes/1233")
                .name("test")
                .length("1km")
                .elevation("567m")
                .created(5454325)
                .build();
        //when
        List<RouteDto> returnedListOfRoutes = stravaRouteRetriever.getExternalRoutes("userIdWithStrava");

        //then
        assertThat(returnedListOfRoutes).containsExactlyInAnyOrder(expectedRoute);
    }
}
