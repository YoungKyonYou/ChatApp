package com.youyk.anchoreerchat.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Component
public class ClockConfig {
    @Bean
    public Clock clock() {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant fixedInstant = Instant.now();
        return Clock.fixed(fixedInstant, zoneId);
    }
}
