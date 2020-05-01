package com.fraczekkrzysztof.gocycling.external.strava;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "strava")
@Getter
@Setter
public class StravaProperties {
    private String clientId;
    private String clientSecret;
    private String baseOAuthAddress;
    private String redirectUri;
    private String baseTokenAddress;
    private String baseDeauthorizationAddress;
    private String apiBaseAddress;
    private String apiRoutes;

}
