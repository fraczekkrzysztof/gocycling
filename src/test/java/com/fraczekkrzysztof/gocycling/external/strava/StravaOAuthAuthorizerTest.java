package com.fraczekkrzysztof.gocycling.external.strava;


import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenRequestDto;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenResponseDto;
import com.fraczekkrzysztof.gocycling.external.strava.model.SummaryAthlete;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class StravaOAuthAuthorizerTest {

    @MockBean
    UserRepository userRepository;
    @MockBean
    @Qualifier("customRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    StravaProperties properties;
    @Autowired
    StravaOAuthAuthorizer stravaAutorizer;

    @Test
    public void shouldReturnValidAuthorizathionLink() {
        //given
        String expectedLink = "https://www.strava.com/oauth/mobile/authorize?client_id=33057&response_type=code&redirect_uri=https://gocyclingapp.com/callback&approval_prompt=force&scope=read";
        //when
        String generatedLink = stravaAutorizer.generateAuthorizationLink();
        //then
        assertThat(generatedLink).isEqualTo(expectedLink);
    }

    @Test
    public void shouldGenerateAccessToken() {
        //given
        AccessTokenResponseDto mockAccessTokenResponse = mock(AccessTokenResponseDto.class);
        when(mockAccessTokenResponse.getAccessToken()).thenReturn("fakeAccessToken");
        when(mockAccessTokenResponse.getRefreshToken()).thenReturn("fakeRefreshToken");
        when(mockAccessTokenResponse.getExpiresAt()).thenReturn((int) Instant.now().getEpochSecond() + 21600);
        SummaryAthlete athlete = mock(SummaryAthlete.class);
        when(athlete.getId()).thenReturn(1000500);
        when(mockAccessTokenResponse.getAthlete()).thenReturn(athlete);
        when(restTemplate.postForEntity("https://www.strava.com/oauth/token?client_id=33057&client_secret=eb3369fc1257a83b1d846a3db8b374e3e8068300&code=fakeCode&grant_type=authorization_code", null, AccessTokenResponseDto.class))
                .thenReturn(ResponseEntity.ok(mockAccessTokenResponse));
        User mockUser = mock(User.class);
        when(userRepository.findById("userId")).thenReturn(Optional.of(mockUser));
        //when
        Throwable thrown = catchThrowable(() -> stravaAutorizer.getAccessToken(new AccessTokenRequestDto("userId", "fakeCode")));
        //then
        assertThat(thrown)
                .doesNotThrowAnyException();
        verify(restTemplate).postForEntity("https://www.strava.com/oauth/token?client_id=33057&client_secret=eb3369fc1257a83b1d846a3db8b374e3e8068300&code=fakeCode&grant_type=authorization_code", null, AccessTokenResponseDto.class);
        verify(userRepository).findById("userId");
        verify(userRepository).save(mockUser);
    }

    @Test
    void shouldRefreshToken() {
        //given
        User userWithStravaConnected = mock(User.class);
        when(userWithStravaConnected.getId()).thenReturn("someUserId");
        UserExternalApp stravaApp = mock(UserExternalApp.class);
        when(stravaApp.getAppType()).thenReturn(ExternalApps.STRAVA);
        when(stravaApp.getExpiresAt()).thenReturn(Instant.now().getEpochSecond() + 5 * 60);
        when(stravaApp.getRefreshToken()).thenReturn("fakeRefreshToken");
        List<UserExternalApp> userExternalAppsList = new ArrayList<>();
        userExternalAppsList.add(stravaApp);
        when(userWithStravaConnected.getExternalAppList()).thenReturn(userExternalAppsList);
        List<User> usersWithStravaConnected = new ArrayList<>();
        usersWithStravaConnected.add(userWithStravaConnected);
        when(userRepository.findAllWithStravaConnected()).thenReturn(usersWithStravaConnected);
        AccessTokenResponseDto mockAccessTokenResponse = mock(AccessTokenResponseDto.class);
        when(mockAccessTokenResponse.getAccessToken()).thenReturn("fakeAccessToken");
        when(mockAccessTokenResponse.getRefreshToken()).thenReturn("fakeRefreshToken");
        when(mockAccessTokenResponse.getExpiresAt()).thenReturn((int) Instant.now().getEpochSecond() + 21600);
        when(restTemplate.postForEntity("https://www.strava.com/oauth/token?client_id=33057&client_secret=eb3369fc1257a83b1d846a3db8b374e3e8068300&grant_type=refresh_token&refresh_token=fakeRefreshToken", null, AccessTokenResponseDto.class))
                .thenReturn(ResponseEntity.ok(mockAccessTokenResponse));

        //when
        Throwable thrown = catchThrowable(() -> stravaAutorizer.refreshToken());
        //then
        assertThat(thrown)
                .doesNotThrowAnyException();
        verify(restTemplate).postForEntity("https://www.strava.com/oauth/token?client_id=33057&client_secret=eb3369fc1257a83b1d846a3db8b374e3e8068300&grant_type=refresh_token&refresh_token=fakeRefreshToken", null, AccessTokenResponseDto.class);
        verify(userRepository).findAllWithStravaConnected();
        verify(userRepository).saveAll(usersWithStravaConnected);
    }

    @Test
    void shouldDeauthorizeUser() {
        //given
        User userWithStravaConnected = mock(User.class);
        UserExternalApp stravaApp = mock(UserExternalApp.class);
        when(stravaApp.getAppType()).thenReturn(ExternalApps.STRAVA);
        when(stravaApp.getAccessToken()).thenReturn("accessTokenToDelete");
        List<UserExternalApp> userExternalAppsList = new ArrayList<>();
        userExternalAppsList.add(stravaApp);
        when(userWithStravaConnected.getExternalAppList()).thenReturn(userExternalAppsList);
        when(userRepository.findById("userToDelete")).thenReturn(Optional.of(userWithStravaConnected));
        when(restTemplate.postForEntity("https://www.strava.com/oauth/deauthorize?access_token=accessTokenToDelete", null, String.class)).thenReturn(ResponseEntity.ok(""));
        //when
        Throwable thrown = catchThrowable(() -> stravaAutorizer.deauthorize("userToDelete"));
        //then
        assertThat(thrown)
                .doesNotThrowAnyException();
        verify(restTemplate).postForEntity("https://www.strava.com/oauth/deauthorize?access_token=accessTokenToDelete", null, String.class);
        verify(userRepository).save(userWithStravaConnected);
    }
}
