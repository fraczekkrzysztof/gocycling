package com.fraczekkrzysztof.gocycling.external;

import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenRequestDto;

public interface ExternalOAuthAuthorizer {

    String generateAuthorizationLink();
    void getAccessToken(AccessTokenRequestDto authorizationCode);
    void refreshToken();
    void deauthorize(String userUid);
}
