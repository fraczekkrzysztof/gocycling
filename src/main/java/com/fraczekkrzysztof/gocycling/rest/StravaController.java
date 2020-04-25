package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.external.ExternalOAuthAuthorizer;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenRequestDto;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/strava")
public class StravaController {

    ExternalOAuthAuthorizer authorizer;

    public StravaController(@Qualifier("stravaOAuthAuthorizer") ExternalOAuthAuthorizer authorizer) {
        this.authorizer = authorizer;
    }

    @GetMapping("/authorizationLink")
    public ResponseEntity<String> getAutorizathionLink(){
        return ResponseEntity.status(HttpStatus.OK).body(authorizer.generateAuthorizationLink());
    }

    @PostMapping("/accessToken")
    public ResponseEntity<AccessTokenResponseDto> getAccessToken(@RequestBody AccessTokenRequestDto accessTokenRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(authorizer.getAccessToken(accessTokenRequestDto));
    }

}
