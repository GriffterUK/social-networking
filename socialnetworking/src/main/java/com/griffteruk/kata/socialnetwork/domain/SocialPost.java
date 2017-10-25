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

    public SocialPost(String message)
    {
        this(Clock.systemDefaultZone(), message);
    }

    public SocialPost(Clock clock, String message)
    {
        this.clock = clock;
        this.message = message;
        this.timestamp = LocalDateTime.now(clock);
    }

    public String getMessage() {

        LocalDateTime dateTimeNow = LocalDateTime.now(clock);

        long daysSincePost = ChronoUnit.DAYS.between(timestamp, dateTimeNow);
        if ( daysSincePost >= 1) {
            return messageWithUnitsPostfix(daysSincePost, DAY_UNIT_NAME);
        }

        long minutesSincePost = ChronoUnit.MINUTES.between(timestamp, dateTimeNow);
        if ( minutesSincePost >= 1) {
            return messageWithUnitsPostfix(minutesSincePost, MINUTE_UNIT_NAME);
        }

        long secondsSincePost = ChronoUnit.SECONDS.between(timestamp, dateTimeNow);
        if (secondsSincePost >= 1) {
            return messageWithUnitsPostfix(secondsSincePost, SECOND_UNIT_NAME);
        }

        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    protected String messageWithUnitsPostfix(long units, String unitName)
    {
        return String.format("%s (%d %s ago)", message, units,
                units > 1 ? unitName + "s" : unitName);
    }
}
