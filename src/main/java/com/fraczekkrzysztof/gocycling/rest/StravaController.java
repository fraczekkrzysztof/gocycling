package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.external.ExternalOAuthAuthorizer;
import com.fraczekkrzysztof.gocycling.external.strava.model.AccessTokenRequestDto;
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
    public ResponseEntity<String> getAccessToken(@RequestBody AccessTokenRequestDto accessTokenRequestDto){
        authorizer.getAccessToken(accessTokenRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(){
        authorizer.refreshToken();
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PostMapping("/deauthorize")
    public ResponseEntity<String> deautorize(@RequestParam("userUid") String userUid){
        authorizer.deauthorize(userUid);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

}