package com.griffteruk.kata.socialnetwork.domain;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by User on 22/10/2017.
 */
public class SocialPost implements Post {

    private static final String DAY_UNIT_NAME = "day";
    private static final String MINUTE_UNIT_NAME = "minute";
    private static final String SECOND_UNIT_NAME = "second";

    private Clock clock;
    private String message;
    private LocalDateTime timestamp;

    public SocialPost(Clock clock, String message)
    {
        this.clock = clock;
        this.message = message;
        this.timestamp = LocalDateTime.now(clock);
    }

    public SocialPost(String message)
    {
        this(Clock.systemDefaultZone(), message);
    }

    public String getMessage() {

        long daysSincePost = ChronoUnit.DAYS.between(LocalDateTime.now(clock), timestamp);
        if ( daysSincePost >= 1) {
            return messageWithUnits(daysSincePost, DAY_UNIT_NAME);
        }

        long minutesSincePost = ChronoUnit.MINUTES.between(LocalDateTime.now(clock), timestamp);
        if ( minutesSincePost >= 1) {
            return messageWithUnits(minutesSincePost, MINUTE_UNIT_NAME);
        }

        long secondsSincePost = ChronoUnit.SECONDS.between(LocalDateTime.now(clock), timestamp);
        if (secondsSincePost >= 1) {
            return messageWithUnits(secondsSincePost, SECOND_UNIT_NAME);
        }

        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    protected String messageWithUnits(long units, String unitName)
    {
        return String.format("%s (%d %s ago)", message, units,
                units > 1 ? unitName + "s": unitName);
    }
}
