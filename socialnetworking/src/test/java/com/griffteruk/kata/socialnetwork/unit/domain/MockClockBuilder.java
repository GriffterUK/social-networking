package com.griffteruk.kata.socialnetwork.unit.domain;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockClockBuilder {

    List<Instant> clockInstants = new ArrayList<>();

    public static MockClockBuilder aMockClock() {
        return new MockClockBuilder();
    }

    public MockClockBuilder withFixedDateTimeOf(LocalDateTime dateTime) {
        clockInstants.add(dateTimeToInstant(dateTime));
        return this;
    }

    public MockClockBuilder withSequenceOfDateTimes(LocalDateTime... dateTimes) {
        for (LocalDateTime dateTime : dateTimes) {
            clockInstants.add(dateTimeToInstant(dateTime));
        }

        return this;
    }

    public Clock build() {
        Clock clock = mock(Clock.class);

        org.mockito.stubbing.OngoingStubbing clockStubbing
                = when(clock.instant());

        for (Instant instant : clockInstants) {
            clockStubbing = clockStubbing.thenReturn(instant);
        }

        when(clock.getZone()).thenReturn(ZoneId.of("UTC").normalized());

        return clock;
    }

    private Instant dateTimeToInstant(LocalDateTime dateTime) {
        return dateTime.atOffset(ZoneOffset.UTC).toInstant();
    }
}
