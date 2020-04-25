package com.fraczekkrzysztof.gocycling.external.strava;

import com.fraczekkrzysztof.gocycling.dao.UserExternalAppsRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import com.fraczekkrzysztof.gocycling.external.ExternalOAuthAuthorizer;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenRequestDto;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Component
public class StravaOAuthAuthorizer implements ExternalOAuthAuthorizer {

    private StravaProperties stravaProperties;
    private UserRepository userRepository;
    private UserExternalAppsRepository userExternalAppsRepository;

    public StravaOAuthAuthorizer(StravaProperties stravaProperties, UserRepository userRepository, UserExternalAppsRepository userExternalAppsRepository) {
        this.stravaProperties = stravaProperties;
        this.userRepository = userRepository;
        this.userExternalAppsRepository = userExternalAppsRepository;
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

        RestTemplate restTemplate = new RestTemplate();
        AccessTokenResponseDto response = restTemplate.postForEntity(sb.toString(),null, AccessTokenResponseDto.class).getBody();
        User user = userRepository.findById(accessTokenRequestDto.getUserUid()).orElseThrow(() ->  new NoSuchElementException("User doesn't exists"));
        UserExternalApp userExternalApp = new UserExternalApp();
        userExternalApp.setUser(user);
        userExternalApp.setAppUserId(response.getAthlete().getId().longValue());
        userExternalApp.setAppType(ExternalApps.STRAVA);
        userExternalApp.setRefreshToken(response.getRefreshToken());
        userExternalApp.setAccessToken(response.getAccessToken());
        userExternalApp.setExpiresAt(response.getExpiresAt().longValue());
        userExternalAppsRepository.save(userExternalApp);
    }
}
