package com.fraczekkrzysztof.gocycling;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gocycling")
@Getter
@Setter
public class GoCyclingProperties {

    private int deleteOldEventsInterval;
    private int deleteOldEventsAfterDays;
}
