package com.fraczekkrzysztof.gocycling;

import com.fraczekkrzysztof.gocycling.dao.UserExternalAppsRepository;
import com.fraczekkrzysztof.gocycling.dto.route.RouteDto;
import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import com.fraczekkrzysztof.gocycling.external.ExternalRoutesRetriever;
import com.fraczekkrzysztof.gocycling.external.strava.StravaProperties;
import com.fraczekkrzysztof.gocycling.external.strava.model.StravaRouteDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StravaExternalRoutesRetrieverTests {
    @Autowired
    StravaProperties stravaProperties;
    @MockBean
    UserExternalAppsRepository userExternalAppsRepository;
    @MockBean
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("stravaExternalRoutesRetriever")
    ExternalRoutesRetriever stravaRouteRetriever;

    @Test
    public void shouldRetrieveOneRoute(){
        UserExternalApp userExternalApp = UserExternalApp.builder()
                .appUserId(12345L)
                .accessToken("fjgdghlfdsg")
                .build();

        StravaRouteDto stravaRouteDto = StravaRouteDto.builder()
                .id(1233L)
                .name("test")
                .distance(1234.45)
                .elevationGain(567.34)
                .timestamp(5454325)
                .build();

        List<StravaRouteDto> forRestTemplateList = Arrays.asList(stravaRouteDto);

        when(userExternalAppsRepository.findStravaByUserUid(anyString())).thenReturn(Optional.of(userExternalApp));
        when(restTemplate.exchange(anyString(),any(),any(),eq(new ParameterizedTypeReference<List<StravaRouteDto>>() {}))).thenReturn(new ResponseEntity<>(forRestTemplateList,HttpStatus.OK));
        List<RouteDto> listOfRoutes = stravaRouteRetriever.getExternalRoutes("agggfsd");
        Assert.assertEquals(1,listOfRoutes.size());
        RouteDto singleRoute = listOfRoutes.get(0);
        Assert.assertEquals(ExternalApps.STRAVA,singleRoute.getAppType());
        Assert.assertEquals("https://www.strava.com/routes/1233",singleRoute.getLink());
    }
}
