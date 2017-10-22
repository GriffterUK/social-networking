package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.domain.UserRepository;
import com.griffteruk.kata.socialnetwork.domain.WithMockedUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by User on 21/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class FollowCommandShould extends WithMockedUserRepository {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    private static final String SOME_EXISTING_USER_NAME = "Jim";
    private static final String SOME_OTHER_EXISTING_USER_NAME = "Bob";

    @Test
    public void returnAnEmptyListAsResult()
    {
        expectUserRepositoryToFindUserByName(SOME_EXISTING_USER_NAME);
        expectUserRepositoryToFindUserByName(SOME_OTHER_EXISTING_USER_NAME);

        assertThat(new FollowCommand(userRepository, SOME_EXISTING_USER_NAME, SOME_OTHER_EXISTING_USER_NAME).process(),
                is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void followUserWhenUserAndUserToFollowBothExist()
    {
        User user = expectUserRepositoryToFindUserByName(SOME_EXISTING_USER_NAME);
        User userToFollow = expectUserRepositoryToFindUserByName(SOME_OTHER_EXISTING_USER_NAME);

        assertThat(new FollowCommand(userRepository, SOME_EXISTING_USER_NAME, SOME_OTHER_EXISTING_USER_NAME).process(),
                is(EMPTY_LIST_OF_STRINGS));

        verify(user).addUserToFollow(userToFollow);
    }

    @Test
    public void notFollowUserWhenUserToFollowDoesNotExist()
    {
        User user = expectUserRepositoryToFindUserByName(SOME_EXISTING_USER_NAME);
        expectUserRepositoryNotToFindUserByName(SOME_OTHER_EXISTING_USER_NAME);

        assertThat(new FollowCommand(userRepository, SOME_EXISTING_USER_NAME, SOME_OTHER_EXISTING_USER_NAME).process(),
                is(EMPTY_LIST_OF_STRINGS));

        verify(user, never()).addUserToFollow(any(User.class));
    }
}
