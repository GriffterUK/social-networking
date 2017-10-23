package com.griffteruk.kata.socialnetwork.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by User on 22/10/2017.
 */
public class SocialUserShould {

    private static final String SOME_USER_NAME = "Jim";
    private static final String SOME_USERS_FIRST_POST = "Hello World!";
    private static final String USER_NAME_TO_FOLLOW = "Bob";

    public static final int NONE = 0;

    private SocialUser someUser;

    @Before
    public void initialise()
    {
        someUser = new SocialUser(SOME_USER_NAME);
    }

    @Test
    public void returnSameNameAsTheOneProvided()
    {
        assertThat(someUser.getName(), is(SOME_USER_NAME));
    }

    @Test
    public void notReturnAnyPostsWhenNoPostsHaveBeenAdded()
    {
        assertThat(numberOfPostsForUser(someUser), is(NONE));
    }

    @Test
    public void returnAllPostsThatWereAdded()
    {
        someUser.addPost(new SocialPost(SOME_USERS_FIRST_POST));

        assertThat(numberOfPostsForUser(someUser), is(1));
        assertThat(firstPostMessageForUser(someUser), is(SOME_USERS_FIRST_POST));
    }

    @Test
    public void notReturnAnyUsersThatHaveNotBeenFollowed()
    {
        assertThat(numberOfFollowedUsersForUser(someUser), is(NONE));
    }

    @Test
    public void returnUsersThatHaveBeenFollowed()
    {
        User userToFollow = new SocialUser(USER_NAME_TO_FOLLOW);
        someUser.addUserToFollow(userToFollow);

        assertThat(numberOfFollowedUsersForUser(someUser), is(1));
        assertThat(firstFollowedUserForUser(someUser), is(userToFollow));
    }

    @Test
    public void notAllowUserToFollowThemselves()
    {
        someUser.addUserToFollow(someUser);
        assertThat(numberOfFollowedUsersForUser(someUser), is(NONE));
    }

    protected int numberOfPostsForUser(User user)
    {
        return user.getPosts().size();
    }

    protected String firstPostMessageForUser(User user)
    {
        return user.getPosts().size() > 0 ? user.getPosts().get(0).getMessage() : "";
    }

    protected int numberOfFollowedUsersForUser(User user)
    {
        return user.getFollowedUsers().size();
    }

    protected User firstFollowedUserForUser(User user)
    {
        return user.getFollowedUsers().size() > 0 ? user.getFollowedUsers().get(0) : null;
    }

}
