package com.fraczekkrzysztof.gocycling.tasks;

import com.fraczekkrzysztof.gocycling.external.ExternalOAuthAuthorizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StravaRefreshScheduler {

    ExternalOAuthAuthorizer stravaAutorizer;

    public StravaRefreshScheduler(@Qualifier("stravaOAuthAuthorizer") ExternalOAuthAuthorizer stravaAutorizer) {
        this.stravaAutorizer = stravaAutorizer;
    }

    @Scheduled(fixedRateString = "${strava.refreshInterval}")
    public void refreshTokensForStrava(){
        stravaAutorizer.refreshToken();
    }
}
