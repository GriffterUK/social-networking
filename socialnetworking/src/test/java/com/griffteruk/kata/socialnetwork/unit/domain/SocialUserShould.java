package com.griffteruk.kata.socialnetwork.unit.domain;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.SocialUser;
import com.griffteruk.kata.socialnetwork.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.griffteruk.kata.socialnetwork.unit.domain.MockPostBuilder.aMockPost;
import static com.griffteruk.test.matchers.StringListMatchers.containsStringsStartingWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Lee Griffiths on 22/10/2017.
 */
public class SocialUserShould {

    private static final String SOME_USER_NAME = "Jim";
    private static final String SOME_USERS_FIRST_POST = "Hello World!";
    private static final String SOME_USERS_SECOND_POST = "How are we today?";
    private static final String USER_NAME_TO_FOLLOW = "Bob";

    public static final int NONE = 0;

    private static final LocalDateTime DATE_TIME_OF_POST_CREATION = LocalDateTime.now();

    private SocialUser someUser;

    @Before
    public void initialise() {
        someUser = new SocialUser(SOME_USER_NAME);
    }

    @Test
    public void returnSameNameAsTheOneProvided() {
        assertThat(someUser.getName(), is(SOME_USER_NAME));
    }

    @Test
    public void notReturnAnyPostsWhenNoPostsHaveBeenAdded() {
        assertThat(numberOfPostsForUser(someUser), is(NONE));
    }

    @Test
    public void returnAllPostsThatWereAdded() {
        Post usersFirstPost = aMockPost()
                .withMessage(SOME_USERS_FIRST_POST)
                .withTimestamp(DATE_TIME_OF_POST_CREATION)
                .build();

        Post usersSecondPost = aMockPost()
                .withMessage(SOME_USERS_SECOND_POST)
                .withTimestamp(DATE_TIME_OF_POST_CREATION)
                .build();

        someUser.addPost(usersFirstPost);
        someUser.addPost(usersSecondPost);

        assertThat(numberOfPostsForUser(someUser), is(2));

        assertThat(usersPostMessages(someUser),
                containsStringsStartingWith(
                        SOME_USERS_FIRST_POST,
                        SOME_USERS_SECOND_POST
                ));
    }

    @Test
    public void notReturnAnyUsersThatHaveNotBeenFollowed() {
        assertThat(numberOfFollowedUsersForUser(someUser), is(NONE));
    }

    @Test
    public void returnUsersThatHaveBeenFollowed() {
        User userToFollow = new SocialUser(USER_NAME_TO_FOLLOW);
        someUser.addUserToFollow(userToFollow);

        assertThat(numberOfFollowedUsersForUser(someUser), is(1));
        assertThat(firstFollowedUserForUser(someUser), is(userToFollow));
    }

    @Test
    public void notAllowUserToFollowThemselves() {
        someUser.addUserToFollow(someUser);
        assertThat(numberOfFollowedUsersForUser(someUser), is(NONE));
    }

    private int numberOfPostsForUser(User user) {
        return user.getPosts().size();
    }

    private List<String> usersPostMessages(User user) {
        return user.getPosts()
                .stream()
                .map(Post::getMessage)
                .collect(Collectors.toList());
    }

    private int numberOfFollowedUsersForUser(User user) {
        return user.getFollowedUsers().size();
    }

    private User firstFollowedUserForUser(User user) {
        return user.getFollowedUsers().size() > 0 ? user.getFollowedUsers().get(0) : null;
    }

}
