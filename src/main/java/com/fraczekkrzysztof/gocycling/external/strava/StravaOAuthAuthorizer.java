package com.fraczekkrzysztof.gocycling.external.strava;

import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import com.fraczekkrzysztof.gocycling.external.ExternalOAuthAuthorizer;
import com.fraczekkrzysztof.gocycling.external.strava.exception.StravaApiException;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenRequestDto;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@Slf4j
@RequiredArgsConstructor
public class StravaOAuthAuthorizer implements ExternalOAuthAuthorizer {

    private final StravaProperties stravaProperties;
    private final UserRepository userRepository;
    @Qualifier("customRestTemplate")
    private final RestTemplate restTemplate;


    @Override
    public String generateAuthorizationLink() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(stravaProperties.getBaseOAuthAddress())
                .queryParam("client_id", stravaProperties.getClientId())
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", stravaProperties.getRedirectUri())
                .queryParam("approval_prompt", "force")
                .queryParam("scope", "read");
        log.debug("Successfully generated authorization link!");
        return uriComponentsBuilder.toUriString();
    }

    @Override
    public void getAccessToken(AccessTokenRequestDto accessTokenRequestDto) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(stravaProperties.getBaseTokenAddress())
                .queryParam("client_id", stravaProperties.getClientId())
                .queryParam("client_secret", stravaProperties.getClientSecret())
                .queryParam("code", accessTokenRequestDto.getAccessToken())
                .queryParam("grant_type", "authorization_code");

        ResponseEntity<AccessTokenResponseDto> response = restTemplate.postForEntity(uriComponentsBuilder.toUriString(), null, AccessTokenResponseDto.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new StravaApiException("There is error during retreiving access token");
        }
        User user = userRepository.findById(accessTokenRequestDto.getUserUid()).orElseThrow(() -> new NoSuchElementException("User doesn't exists"));
        UserExternalApp userExternalApp = UserExternalApp.builder()
                .appUserId(response.getBody().getAthlete().getId().longValue())
                .appType(ExternalApps.STRAVA)
                .refreshToken(response.getBody().getRefreshToken())
                .accessToken(response.getBody().getAccessToken())
                .expiresAt(response.getBody().getExpiresAt().longValue()).build();
        user.getExternalAppList().add(userExternalApp);
        userRepository.save(user);
        log.debug("Successfully retrieve access token and store it in db");
    }

    @Override
    public void refreshToken() {
        log.debug("Starting refreshing token");
        List<User> usersWithStrava = userRepository.findAllWithStravaConnected();
        for (User u : usersWithStrava) {
            u.getExternalAppList().stream()
                    .filter(ea -> ExternalApps.STRAVA.equals(ea.getAppType()))
                    .findFirst().ifPresent(externalApp -> {
                if (((externalApp.getExpiresAt() * 1000) - 40 * 60 * 1000) < System.currentTimeMillis()) {
                    try {
                        refreshToken(externalApp);
                        log.debug(String.format("Successfully refresh strava token for user %s", u.getId()));
                    } catch (Exception e) {
                        log.error(String.format("Error during refreshing token for user %s, at time %d, using refresh token %s", u.getId(), System.currentTimeMillis(), externalApp.getRefreshToken()), e);
                    }
                }
            });
        }
        userRepository.saveAll(usersWithStrava);
        log.debug("Refreshing token finished");
    }

    @Override
    public void deauthorize(String userUid) {
        User user = userRepository.findById(userUid)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no user of id %s ", userUid)));
        UserExternalApp externalApp = user
                .getExternalAppList().stream()
                .filter(uea -> ExternalApps.STRAVA.equals(uea.getAppType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("User %s doesn't have strava connected", userUid)));
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(stravaProperties.getBaseDeauthorizationAddress())
                .queryParam("access_token", externalApp.getAccessToken());
        ResponseEntity<String> response = restTemplate.postForEntity(uriComponentsBuilder.toUriString(), null, String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new StravaApiException("There is error during retrieving access token");
        }
        user.getExternalAppList().remove(externalApp);
        userRepository.save(user);
        log.debug("Successfully deautorize user {0}", userUid);
    }

    private void refreshToken(UserExternalApp externalApp) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(stravaProperties.getBaseTokenAddress())
                .queryParam("client_id", stravaProperties.getClientId())
                .queryParam("client_secret", stravaProperties.getClientSecret())
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", externalApp.getRefreshToken());
        String refreshUrl = uriComponentsBuilder.toUriString();
        log.debug("Refreshing access token using address " + refreshUrl);
        ResponseEntity<AccessTokenResponseDto> response = restTemplate.postForEntity(refreshUrl, null, AccessTokenResponseDto.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new StravaApiException("There is error during retrieving access token");
        }
        externalApp.setAccessToken(response.getBody().getAccessToken());
        externalApp.setRefreshToken(response.getBody().getRefreshToken());
        externalApp.setExpiresAt(response.getBody().getExpiresAt().longValue());
    }
}
