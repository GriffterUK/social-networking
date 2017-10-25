package com.griffteruk.kata.socialnetwork.unit.domain;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.SocialPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Created by Lee Griffiths on 22/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SocialPostShould {

    private static final String SOME_POST_MESSAGE = "This is a message";

    private static final int ONE_DAY = 1;
    private static final int TWO_DAYS = 2 * ONE_DAY;

    private static final int ONE_MINUTE = 1;
    private static final int FIVE_MINUTES = 5 * ONE_MINUTE;

    private static final int ONE_SECOND = 1;
    private static final int TWELVE_SECONDS = 12 * ONE_SECOND;

    private static final LocalDateTime DATE_TIME_OF_POST_CREATION = LocalDateTime.now();

    @Test
    public void returnSameMessageAsTheOneProvided()
    {
        Post somePost = new SocialPost(SOME_POST_MESSAGE);
        assertThat(somePost.getMessage(), is(SOME_POST_MESSAGE));
    }

    @Test
    public void returnTimestampOfWhenPostWasFirstCreated()
    {
        Clock clock = MockClockBuilder.aMockClock()
                .withFixedDateTimeOf(DATE_TIME_OF_POST_CREATION)
                .build();

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getTimestamp(), is(DATE_TIME_OF_POST_CREATION));

        verify(clock).instant();
    }

    @Test
    public void returnMessageWithDayPostfixWhenPostWasCreatedExactlyOneDayAgo()
    {
        Clock clock = MockClockBuilder.aMockClock()
                .withSequenceOfDateTimes(
                        DATE_TIME_OF_POST_CREATION,
                        daysSince(DATE_TIME_OF_POST_CREATION, ONE_DAY))
                .build();

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getMessage().contains("1 day ago"), is(true));
    }

    @Test
    public void returnMessageWithDaysPostfixWhenPostWasCreatedMoreThanOneDayAgo()
    {
        Clock clock = MockClockBuilder.aMockClock()
                .withSequenceOfDateTimes(
                        DATE_TIME_OF_POST_CREATION,
                        daysSince(DATE_TIME_OF_POST_CREATION, TWO_DAYS))
                .build();

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getMessage().contains("2 days ago"), is(true));
    }

    @Test
    public void returnMessageWithMinutePostfixWhenPostWasCreatedExactlyOneMinuteAgo()
    {
        Clock clock = MockClockBuilder.aMockClock()
                .withSequenceOfDateTimes(
                        DATE_TIME_OF_POST_CREATION,
                        minutesSince(DATE_TIME_OF_POST_CREATION, ONE_MINUTE))
                .build();

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getMessage().contains("1 minute ago"), is(true));
    }

    @Test
    public void returnMessageWithMinutesPostfixWhenPostWasCreatedMoreThanOneMinuteAgo()
    {
        Clock clock = MockClockBuilder.aMockClock()
                .withSequenceOfDateTimes(
                        DATE_TIME_OF_POST_CREATION,
                        minutesSince(DATE_TIME_OF_POST_CREATION, FIVE_MINUTES))
                .build();

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getMessage().contains("5 minutes ago"), is(true));
    }

    @Test
    public void returnMessageWitSecondPostfixWhenPostWasCreatedExactlyOneSecondAgo()
    {
        Clock clock = MockClockBuilder.aMockClock()
                .withSequenceOfDateTimes(
                        DATE_TIME_OF_POST_CREATION,
                        secondsSince(DATE_TIME_OF_POST_CREATION, ONE_SECOND))
                .build();

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getMessage().contains("1 second ago"), is(true));
    }

    @Test
    public void returnMessageWithSecondsPostfixWhenPostWasCreatedMoreThanOneSecondAgo()
    {
        Clock clock = MockClockBuilder.aMockClock()
                .withSequenceOfDateTimes(
                        DATE_TIME_OF_POST_CREATION,
                        secondsSince(DATE_TIME_OF_POST_CREATION, TWELVE_SECONDS))
                .build();

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getMessage().contains("12 seconds ago"), is(true));
    }

    protected LocalDateTime daysSince(LocalDateTime localDateTime, int numberOfDays)
    {
        return localDateTime.minusDays(numberOfDays * -1);
    }

    protected LocalDateTime minutesSince(LocalDateTime localDateTime, int numberOfMinutes)
    {
        return localDateTime.minusMinutes(numberOfMinutes * -1);
    }

    protected LocalDateTime secondsSince(LocalDateTime localDateTime, int numberOfSeconds)
    {
        return localDateTime.minusSeconds(numberOfSeconds * -1);
    }
}
