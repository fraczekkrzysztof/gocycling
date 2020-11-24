package com.fraczekkrzysztof.gocycling;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StravaOAuthAutorizerTests {

//    @Autowired
//    private StravaProperties stravaProperties;
//    @MockBean
//    private UserRepository userRepository;
//    @MockBean
//    @Qualifier("customRestTemplate")
//    private RestTemplate restTemplate;
//    @Autowired
//    @Qualifier("stravaOAuthAuthorizer")
//    ExternalOAuthAuthorizer stravaAutorizer;
//
//    @Test
//    public void shouldReturnValidAuthorizathionLink() throws MalformedURLException, URISyntaxException {
//        String authorizationLink = stravaAutorizer.generateAuthorizationLink();
//        URI uri = new URL(authorizationLink).toURI();
//        Assert.assertTrue(true);
//    }
//
//    @Test
//    public void shouldGenerateAccessToken(){
//        AccessTokenRequestDto accessTokenRequestDto = new AccessTokenRequestDto();
//        accessTokenRequestDto.setAccessToken("dghrhrthgfdsgsh");
//        accessTokenRequestDto.setUserUid("14543543fdsg");
//        AccessTokenResponseDto accessTokenResponseDto = new AccessTokenResponseDto();
//        accessTokenResponseDto.setAccessToken("ACCESSTOKEN");
//        accessTokenResponseDto.setExpiresAt(24554353);
//        SummaryAthlete athlete = new SummaryAthlete();
//        athlete.setId(123);
//        accessTokenResponseDto.setAthlete(athlete);
//        User user = new User();
//        user.setId("55555");
//        user.setName("TEST");
//        when(restTemplate.postForEntity(anyString(),any(),(Class<AccessTokenResponseDto>)any())).thenReturn(new ResponseEntity<>(accessTokenResponseDto, HttpStatus.OK));
//        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
//        when(userExternalAppsRepository.save(any(UserExternalApp.class))).thenReturn(new UserExternalApp());
//        stravaAutorizer.getAccessToken(accessTokenRequestDto);
//        verify(restTemplate).postForEntity(anyString(),any(),(Class<AccessTokenResponseDto>)any());
//        verify(userRepository).findById(anyString());
//        verify(userExternalAppsRepository).save(any(UserExternalApp.class));
//    }

//    @Test
//    public void shouldDeauthorize(){
//        //TODO modify this test to fit in new flow
//        UserExternalApp userExternalApp = new UserExternalApp();
//        userExternalApp.setRefreshToken("REFRESHTOKEN");
//        when(userExternalAppsRepository.findStravaByUserUid(anyString())).thenReturn(Optional.of(userExternalApp));
//        doNothing().when(userExternalAppsRepository).delete(any());
//        when(restTemplate.postForEntity(anyString(),any(),any())).thenReturn(new ResponseEntity<>(String.class, HttpStatus.OK));
//        stravaAutorizer.deauthorize("1234");
//        verify(restTemplate).postForEntity(anyString(),any(),(Class<AccessTokenResponseDto>)any());
//        verify(userExternalAppsRepository).delete(any());
//        verify(userExternalAppsRepository).findStravaByUserUid(anyString());
//    }

}
