package com.griffteruk.kata.socialnetwork.unit.domain;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.SocialPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Clock;
import java.time.LocalDateTime;

import static com.griffteruk.kata.socialnetwork.unit.domain.MockClockBuilder.aMockClock;
import static org.hamcrest.CoreMatchers.containsString;
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
    public void returnSameMessageAsTheOneProvided() {
        Post somePost = new SocialPost(SOME_POST_MESSAGE);
        assertThat(somePost.getMessage(), is(SOME_POST_MESSAGE));
    }

    @Test
    public void returnTimestampOfWhenPostWasFirstCreated() {
        Clock clock = aMockClock()
                .withFixedDateTimeOf(DATE_TIME_OF_POST_CREATION)
                .build();

        Post newPost = new SocialPost(clock, SOME_POST_MESSAGE);

        assertThat(newPost.getTimestamp(), is(DATE_TIME_OF_POST_CREATION));

        verify(clock).instant();
    }

    @Test
    public void returnMessageWithDayPostfixWhenPostWasCreatedExactlyOneDayAgo() {
        Post newPost = postAtRetrievalDateTimeOf(
                daysSince(DATE_TIME_OF_POST_CREATION, ONE_DAY)
        );

        assertThat(newPost.getMessage(), containsString("1 day ago"));
    }

    @Test
    public void returnMessageWithDaysPostfixWhenPostWasCreatedMoreThanOneDayAgo() {
        Post newPost = postAtRetrievalDateTimeOf(
                daysSince(DATE_TIME_OF_POST_CREATION, TWO_DAYS)
        );

        assertThat(newPost.getMessage(), containsString("2 days ago"));
    }

    @Test
    public void returnMessageWithMinutePostfixWhenPostWasCreatedExactlyOneMinuteAgo() {
        Post newPost = postAtRetrievalDateTimeOf(
                minutesSince(DATE_TIME_OF_POST_CREATION, ONE_MINUTE)
        );

        assertThat(newPost.getMessage(), containsString("1 minute ago"));
    }

    @Test
    public void returnMessageWithMinutesPostfixWhenPostWasCreatedMoreThanOneMinuteAgo() {
        Post newPost = postAtRetrievalDateTimeOf(
                minutesSince(DATE_TIME_OF_POST_CREATION, FIVE_MINUTES)
        );

        assertThat(newPost.getMessage(), containsString("5 minutes ago"));
    }

    @Test
    public void returnMessageWitSecondPostfixWhenPostWasCreatedExactlyOneSecondAgo() {
        Post newPost = postAtRetrievalDateTimeOf(
                secondsSince(DATE_TIME_OF_POST_CREATION, ONE_SECOND)
        );

        assertThat(newPost.getMessage(), containsString("1 second ago"));
    }

    @Test
    public void returnMessageWithSecondsPostfixWhenPostWasCreatedMoreThanOneSecondAgo() {
        Post newPost = postAtRetrievalDateTimeOf(
                secondsSince(DATE_TIME_OF_POST_CREATION, TWELVE_SECONDS)
        );

        assertThat(newPost.getMessage(), containsString("12 seconds ago"));
    }

    private Post postAtRetrievalDateTimeOf(LocalDateTime localDateTime) {
        Clock clock = aMockClock()
                .withSequenceOfDateTimes(
                        DATE_TIME_OF_POST_CREATION,
                        localDateTime)
                .build();

        return new SocialPost(clock, SOME_POST_MESSAGE);
    }

    private LocalDateTime daysSince(LocalDateTime localDateTime, int numberOfDays) {
        return localDateTime.minusDays(numberOfDays * -1);
    }

    private LocalDateTime minutesSince(LocalDateTime localDateTime, int numberOfMinutes) {
        return localDateTime.minusMinutes(numberOfMinutes * -1);
    }

    private LocalDateTime secondsSince(LocalDateTime localDateTime, int numberOfSeconds) {
        return localDateTime.minusSeconds(numberOfSeconds * -1);
    }
}
