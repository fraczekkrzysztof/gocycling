package com.fraczekkrzysztof.gocycling;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StravaExternalRoutesRetrieverTests {
//    @Autowired
//    StravaProperties stravaProperties;
//    @MockBean
//    UserExternalAppsRepository userExternalAppsRepository;
//    @MockBean
//    RestTemplate restTemplate;
//
//    @Autowired
//    @Qualifier("stravaExternalRoutesRetriever")
//    ExternalRoutesRetriever stravaRouteRetriever;
//
//    @Test
//    public void shouldRetrieveOneRoute(){
//        UserExternalApp userExternalApp = UserExternalApp.builder()
//                .appUserId(12345L)
//                .accessToken("fjgdghlfdsg")
//                .build();
//
//        StravaRouteDto stravaRouteDto = StravaRouteDto.builder()
//                .id(1233L)
//                .name("test")
//                .distance(1234.45)
//                .elevationGain(567.34)
//                .timestamp(5454325)
//                .build();
//
//        List<StravaRouteDto> forRestTemplateList = Arrays.asList(stravaRouteDto);
//
//        when(userExternalAppsRepository.findStravaByUserUid(anyString())).thenReturn(Optional.of(userExternalApp));
//        when(restTemplate.exchange(anyString(),any(),any(),eq(new ParameterizedTypeReference<List<StravaRouteDto>>() {}))).thenReturn(new ResponseEntity<>(forRestTemplateList,HttpStatus.OK));
//        List<RouteDto> listOfRoutes = stravaRouteRetriever.getExternalRoutes("agggfsd");
//        Assert.assertEquals(1,listOfRoutes.size());
//        RouteDto singleRoute = listOfRoutes.get(0);
//        Assert.assertEquals(ExternalApps.STRAVA,singleRoute.getAppType());
//        Assert.assertEquals("https://www.strava.com/routes/1233",singleRoute.getLink());
//    }
}
