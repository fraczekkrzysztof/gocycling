package com.fraczekkrzysztof.gocycling.external.strava;

import com.fraczekkrzysztof.gocycling.dao.UserExternalAppsRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import com.fraczekkrzysztof.gocycling.external.ExternalOAuthAuthorizer;
import com.fraczekkrzysztof.gocycling.external.strava.exception.StravaApiException;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenRequestDto;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class StravaOAuthAuthorizer implements ExternalOAuthAuthorizer {

    private StravaProperties stravaProperties;
    private UserRepository userRepository;
    private UserExternalAppsRepository userExternalAppsRepository;
    private RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(StravaOAuthAuthorizer.class);

    public StravaOAuthAuthorizer(StravaProperties stravaProperties,
                                 UserRepository userRepository,
                                 UserExternalAppsRepository userExternalAppsRepository,
                                 @Qualifier("customRestTemplate") RestTemplate restTemplate) {
        this.stravaProperties = stravaProperties;
        this.userRepository = userRepository;
        this.userExternalAppsRepository = userExternalAppsRepository;
        this.restTemplate = restTemplate;
    }

    @Autowired


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
       logger.debug("Successfully generated authorization link!");
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
        UserExternalApp userExternalApp = new UserExternalApp();
        userExternalApp.setUser(user);
        userExternalApp.setAppUserId(response.getBody().getAthlete().getId().longValue());
        userExternalApp.setAppType(ExternalApps.STRAVA);
        userExternalApp.setRefreshToken(response.getBody().getRefreshToken());
        userExternalApp.setAccessToken(response.getBody().getAccessToken());
        userExternalApp.setExpiresAt(response.getBody().getExpiresAt().longValue());
        userExternalAppsRepository.save(userExternalApp);
        logger.debug("Successfully retrieve access token and store it in db");
    }

    @Override
    public void refreshToken() {
        logger.debug("Starting refreshing token");
        List<UserExternalApp> listOfExternalApp = userExternalAppsRepository.findAll().stream()
                .filter(e -> e.getAppType() == ExternalApps.STRAVA).collect(Collectors.toList());
        for(UserExternalApp ea : listOfExternalApp){
            if ((ea.getExpiresAt()*1000)<System.currentTimeMillis()){
                refreshToken(ea);
            }
        }
        userExternalAppsRepository.saveAll(listOfExternalApp);
        logger.debug("Refreshing token finished");
    }

    @Override
    public void deauthorize(String userUid) {
        UserExternalApp externalApp = userExternalAppsRepository.findStravaByUserUid(userUid)
                .orElseThrow(() -> new NoSuchElementException("There is no strava connection for user"));
        StringBuilder sb = new StringBuilder();
        sb.append(stravaProperties.getBaseDeauthorizationAddress());
        sb.append("?");
        sb.append("access_token=");
        sb.append(externalApp.getAccessToken());
        ResponseEntity<String> response = restTemplate.postForEntity(sb.toString(),null, String.class);
        if (!response.getStatusCode().is2xxSuccessful()){
            throw new StravaApiException("There is error during retrieving access token");
        }
        userExternalAppsRepository.delete(externalApp);
        logger.debug("Successfully deautorize user {0}",userUid);
    }

    private void refreshToken(UserExternalApp externalApp){
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
        ResponseEntity<AccessTokenResponseDto> response = restTemplate.postForEntity(sb.toString(),null, AccessTokenResponseDto.class);
        if (!response.getStatusCode().is2xxSuccessful()){
            throw new StravaApiException("There is error during retrieving access token");
        }
        externalApp.setAccessToken(response.getBody().getAccessToken());
        externalApp.setRefreshToken(response.getBody().getRefreshToken());
        externalApp.setExpiresAt(response.getBody().getExpiresAt().longValue());
        logger.debug("Successfully refresh token for user {0}",externalApp.getUser().getId());
    }
}
