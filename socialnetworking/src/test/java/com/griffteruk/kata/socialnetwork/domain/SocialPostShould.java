package com.griffteruk.kata.socialnetwork.domain;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.*;

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

    protected Instant whenClockIsFixed()
    {
        LocalDateTime dateTime = LocalDateTime.now();

        Instant instantWhenPostFirstCreated = dateTime.atOffset(ZoneOffset.UTC).toInstant();
        when(clock.instant()).thenReturn(instantWhenPostFirstCreated);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC").normalized());

        return instantWhenPostFirstCreated;
    }
}
