package com.fraczekkrzysztof.gocycling.tasks;

import com.fraczekkrzysztof.gocycling.external.ExternalOAuthAuthorizer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StravaRefreshScheduler {

    private final ExternalOAuthAuthorizer stravaAutorizer;

    @Scheduled(initialDelay = 1000, fixedRateString = "${strava.refreshInterval}")
    public void refreshTokensForStrava(){
        stravaAutorizer.refreshToken();
    }
}
