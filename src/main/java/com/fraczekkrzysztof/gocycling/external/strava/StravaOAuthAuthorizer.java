package com.fraczekkrzysztof.gocycling.external.strava;

import com.fraczekkrzysztof.gocycling.external.ExternalOAuthAuthorizer;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenRequestDto;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StravaOAuthAuthorizer implements ExternalOAuthAuthorizer {

    private StravaProperties stravaProperties;

    @Autowired
    public StravaOAuthAuthorizer(StravaProperties stravaProperties) {
        this.stravaProperties = stravaProperties;
    }

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
    public AccessTokenResponseDto getAccessToken(AccessTokenRequestDto accessTokenRequestDto){
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
        return restTemplate.postForEntity(sb.toString(),null, AccessTokenResponseDto.class).getBody();
    }
}
