package com.griffteruk.kata.socialnetwork.domain;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Created by User on 22/10/2017.
 */
public class SocialPost implements Post {

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
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
