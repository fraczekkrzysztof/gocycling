package com.fraczekkrzysztof.gocycling.external;

import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenRequestDto;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenResponseDto;

public interface ExternalOAuthAuthorizer {

    String generateAuthorizationLink();
    AccessTokenResponseDto getAccessToken(AccessTokenRequestDto authorizationCode);
}
