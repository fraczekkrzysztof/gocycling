package com.fraczekkrzysztof.gocycling.external.strava;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "strava")
public class StravaProperties {
    private String clientId;
    private String clientSecret;
    private String baseOAuthAddress;
    private String redirectUri;
    private String baseTokenAddress;
    private String baseDeauthorizationAddress;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getBaseOAuthAddress() {
        return baseOAuthAddress;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getBaseTokenAddress() {
        return baseTokenAddress;
    }

    public String getBaseDeauthorizationAddress() {
        return baseDeauthorizationAddress;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setBaseOAuthAddress(String baseOAuthAddress) {
        this.baseOAuthAddress = baseOAuthAddress;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public void setBaseTokenAddress(String baseTokenAddress) {
        this.baseTokenAddress = baseTokenAddress;
    }

    public void setBaseDeauthorizationAddress(String baseDeauthorizationAddress) {
        this.baseDeauthorizationAddress = baseDeauthorizationAddress;
    }
}
