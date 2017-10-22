package com.griffteruk.kata.socialnetwork.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by User on 22/10/2017.
 */
public class UserShould {

    private static final String SOME_USER_NAME = "Jim";
    private static final String SOME_USERS_FIRST_POST = "Hello World!";

    private User someUser;

    @Before
    public void initialise()
    {
        someUser = new User(SOME_USER_NAME);
    }

    @Test
    public void returnSameNameAsTheOneProvided()
    {
        assertThat(someUser.getName(), is(SOME_USER_NAME));
    }

    @Test
    public void returnAllPostsThatWereAdded()
    {
        someUser.addPost(SOME_USERS_FIRST_POST);
        assertThat(numberOfPostsForUser(someUser), is(1));
        assertThat(firstPostForUser(someUser), is(SOME_USERS_FIRST_POST));
    }

    protected int numberOfPostsForUser(User user)
    {
        return user.getPosts().size();
    }

    protected String firstPostForUser(User user)
    {
        return user.getPosts().size() > 0 ? user.getPosts().get(0) : "";
    }
}
