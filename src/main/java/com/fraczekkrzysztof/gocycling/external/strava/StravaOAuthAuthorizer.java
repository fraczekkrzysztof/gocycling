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
       StringBuilder sb = new StringBuilder();
       sb.append(stravaProperties.getBaseOAuthAddress());
       sb.append("?");
       sb.append("client_id=");
       sb.append(stravaProperties.getClientId());
       sb.append("&");
       sb.append("response_type=code");
       sb.append("&");
       sb.append("redirect_uri=");
       sb.append(stravaProperties.getRedirectUri());
       sb.append("&");
       sb.append("approval_prompt=force");
       sb.append("&");
       sb.append("scope=read");
       log.debug("Successfully generated authorization link!");
       return sb.toString();
    }

    @Override
    public void getAccessToken(AccessTokenRequestDto accessTokenRequestDto){
        StringBuilder sb = new StringBuilder();
        sb.append(stravaProperties.getBaseTokenAddress());
        sb.append("?");
        sb.append("client_id=");
        sb.append(stravaProperties.getClientId());
        sb.append("&");
        sb.append("client_secret=");
        sb.append(stravaProperties.getClientSecret());
        sb.append("&");
        sb.append("code=");
        sb.append(accessTokenRequestDto.getAccessToken());
        sb.append("&");
        sb.append("grant_type=");
        sb.append("authorization_code");

        ResponseEntity<AccessTokenResponseDto> response = restTemplate.postForEntity(sb.toString(),null, AccessTokenResponseDto.class);
        if (!response.getStatusCode().is2xxSuccessful()){
            throw new StravaApiException("There is error during retreiving access token");
        }
        User user = userRepository.findById(accessTokenRequestDto.getUserUid()).orElseThrow(() ->  new NoSuchElementException("User doesn't exists"));
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
            UserExternalApp externalApp = u.getExternalAppList().stream()
                    .filter(ea -> ExternalApps.STRAVA.equals(ea.getAppType()))
                    .findFirst().get(); //NOSONAR in query there is limitation only for user which have strava connected
            if (((externalApp.getExpiresAt() * 1000) - 40 * 60 * 1000) < System.currentTimeMillis()) {
                refreshToken(externalApp);
            }
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
        StringBuilder sb = new StringBuilder();
        sb.append(stravaProperties.getBaseDeauthorizationAddress());
        sb.append("?");
        sb.append("access_token=");
        sb.append(externalApp.getAccessToken());
        ResponseEntity<String> response = restTemplate.postForEntity(sb.toString(),null, String.class);
        if (!response.getStatusCode().is2xxSuccessful()){
            throw new StravaApiException("There is error during retrieving access token");
        }
        user.getExternalAppList().remove(externalApp);
        log.debug("Successfully deautorize user {0}",userUid);
    }

    private void refreshToken(UserExternalApp externalApp){
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(stravaProperties.getBaseTokenAddress());
            sb.append("?");
            sb.append("client_id=");
            sb.append(stravaProperties.getClientId());
            sb.append("&");
            sb.append("client_secret=");
            sb.append(stravaProperties.getClientSecret());
            sb.append("&");
            sb.append("grant_type=");
            sb.append("refresh_token");
            sb.append("&");
            sb.append("refresh_token=");
            sb.append(externalApp.getRefreshToken());
            log.info("Refreshing access token using address " + sb.toString());
            ResponseEntity<AccessTokenResponseDto> response = restTemplate.postForEntity(sb.toString(),null, AccessTokenResponseDto.class);
            if (!response.getStatusCode().is2xxSuccessful()){
                throw new StravaApiException("There is error during retrieving access token");
            }
            externalApp.setAccessToken(response.getBody().getAccessToken());
            externalApp.setRefreshToken(response.getBody().getRefreshToken());
            externalApp.setExpiresAt(response.getBody().getExpiresAt().longValue());
            //TODO think about proper logging
            //log.debug("Successfully refresh token for user {0}",externalApp.getUser().getId());
        } catch (Exception e){
            //TODO think about proper logging
            //log.error(String.format("Error during refreshing token for user %s, at time %d, using refresh token %s",externalApp.getUser().getId(),System.currentTimeMillis(),externalApp.getRefreshToken()),e);
        }
    }
}
