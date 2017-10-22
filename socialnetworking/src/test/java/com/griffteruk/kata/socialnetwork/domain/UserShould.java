package com.griffteruk.kata.socialnetwork.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by User on 22/10/2017.
 */
public class UserShould {

    private static final String SOME_USER_NAME = "Jim";

    @Test
    public void returnSameNameAsTheOneProvided()
    {
        User someUser = new User(SOME_USER_NAME);
        assertThat(someUser.getName(), is(SOME_USER_NAME));
    }
}
