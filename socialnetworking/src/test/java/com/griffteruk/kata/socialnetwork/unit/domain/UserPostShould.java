package com.griffteruk.kata.socialnetwork.unit.domain;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.domain.UserPost;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.griffteruk.kata.socialnetwork.unit.common.Posts.FIRST_POST_OF_NEW_USER;
import static com.griffteruk.kata.socialnetwork.unit.common.Users.NEW_USER_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by User on 25/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class UserPostShould {

    private User user;
    private Post post;
    private UserPost userPost;

    @Before
    public void initialise()
    {
        user = MockUserBuilder
                .aMockUser()
                .withName(NEW_USER_NAME)
                .build();

        post = MockPostBuilder
                .aMockPost()
                .withMessage(FIRST_POST_OF_NEW_USER)
                .build();

        userPost = new UserPost(user, post);
    }

    @Test
    public void returnSameUserAsProvided()
    {
        assertThat(userPost.getUser(), is(user));
    }

    @Test
    public void returnSamePostAsProvided()
    {
        assertThat(userPost.getPost(), is(post));
    }

    @Test
    public void combineUserNameAndPostMessageInToString()
    {
        String combinedUserNameAndPostMessage =
                user.getName() + " : " + post.getMessage();

        assertThat(userPost.toString(), is(combinedUserNameAndPostMessage));
    }
}
