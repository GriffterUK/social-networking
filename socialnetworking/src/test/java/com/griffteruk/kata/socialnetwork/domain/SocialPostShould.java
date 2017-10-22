package com.griffteruk.kata.socialnetwork.domain;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.cglib.core.Local;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by User on 22/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SocialPostShould {

    private static final String SOME_POST_MESSAGE = "This is a message";

    private Post somePost;

    @Mock
    private Clock clock;

    @Before
    public void initialise()
    {
        somePost = new SocialPost(SOME_POST_MESSAGE);
    }

    @Test
    public void returnSameMessageAsTheOneProvided()
    {
        assertThat(somePost.getMessage(), is(SOME_POST_MESSAGE));
    }

    @Test
    public void returnTimestampOfWhenPostWasFirstCreated()
    {
        Instant fixedInstant = whenClockIsFixed();

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getTimestamp().toInstant(ZoneOffset.UTC), is(fixedInstant));

        verify(clock).instant();
    }

    @Test
    public void returnMessageWithDayPostfixWhenPostWasCreatedExactlyOneDayAgo()
    {
        LocalDateTime timeWhenPostWasCreated = LocalDateTime.now();
        LocalDateTime oneDaySincePostWasCreated = timeWhenPostWasCreated.minusDays(1);

        whenClockReportsInOrderWith(
                timeWhenPostWasCreated,
                oneDaySincePostWasCreated);

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getMessage().contains("1 day ago"), is(true));
    }

    @Test
    public void returnMessageWithDaysPostfixWhenPostWasCreatedMoreThanOneDayAgo()
    {
        LocalDateTime timeWhenPostWasCreated = LocalDateTime.now();
        LocalDateTime fiveDaysSincePostWasCreated = timeWhenPostWasCreated.minusDays(5);

        whenClockReportsInOrderWith(
                timeWhenPostWasCreated,
                fiveDaysSincePostWasCreated);

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getMessage().contains("5 days ago"), is(true));
    }

    @Test
    public void returnMessageWithMinutePostfixWhenPostWasCreatedExactlyOneMinuteAgo()
    {
        LocalDateTime timeWhenPostWasCreated = LocalDateTime.now();
        LocalDateTime oneMinuteSincePostWasCreated = timeWhenPostWasCreated.minusMinutes(1);

        whenClockReportsInOrderWith(
                timeWhenPostWasCreated,
                oneMinuteSincePostWasCreated);

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getMessage().contains("1 minute ago"), is(true));
    }

    @Test
    public void returnMessageWithMinutesPostfixWhenPostWasCreatedMoreThanOneMinuteAgo()
    {
        LocalDateTime timeWhenPostWasCreated = LocalDateTime.now();
        LocalDateTime fiveMinuteSincePostWasCreated = timeWhenPostWasCreated.minusMinutes(5);

        whenClockReportsInOrderWith(
                timeWhenPostWasCreated,
                fiveMinuteSincePostWasCreated);

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getMessage().contains("5 minutes ago"), is(true));
    }

    @Test
    public void returnMessageWitSecondPostfixWhenPostWasCreatedExactlyOneSecondAgo()
    {
        LocalDateTime timeWhenPostWasCreated = LocalDateTime.now();
        LocalDateTime oneSecondSincePostWasCreated = timeWhenPostWasCreated.minusSeconds(1);

        whenClockReportsInOrderWith(
                timeWhenPostWasCreated,
                oneSecondSincePostWasCreated);

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getMessage().contains("1 second ago"), is(true));
    }

    @Test
    public void returnMessageWithSecondsPostfixWhenPostWasCreatedMoreThanOneSecondAgo()
    {
        LocalDateTime timeWhenPostWasCreated = LocalDateTime.now();
        LocalDateTime fiveSecondsSincePostWasCreated = timeWhenPostWasCreated.minusSeconds(5);

        whenClockReportsInOrderWith(
                timeWhenPostWasCreated,
                fiveSecondsSincePostWasCreated);

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getMessage().contains("5 seconds ago"), is(true));
    }

    protected Instant whenClockIsFixed()
    {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        return whenClockReportsInOrderWith(dateTimeNow).get(0);
    }

    protected List<Instant> whenClockReportsInOrderWith(LocalDateTime... dateTimes)
    {
        org.mockito.stubbing.OngoingStubbing clockStubbing = when(clock.instant());

        List<Instant> clockInstants = new ArrayList<>();
        for (LocalDateTime dateTime : dateTimes) {
            Instant dateTimeInstant = dateTime.atOffset(ZoneOffset.UTC).toInstant();
            clockInstants.add(dateTimeInstant);

            clockStubbing = clockStubbing.thenReturn(dateTimeInstant);
        }

        when(clock.getZone()).thenReturn(ZoneId.of("UTC").normalized());

        return clockInstants;
    }
}
