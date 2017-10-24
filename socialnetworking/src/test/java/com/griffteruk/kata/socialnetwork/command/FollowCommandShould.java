package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.*;
import com.griffteruk.kata.socialnetwork.repositories.MockUserRepositoryBuilder;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.griffteruk.kata.socialnetwork.common.Lists.EMPTY_LIST_OF_STRINGS;
import static com.griffteruk.kata.socialnetwork.common.Users.NON_EXISTENT_USER_NAME;
import static com.griffteruk.kata.socialnetwork.common.Users.SOME_EXISTING_USER_NAME;
import static com.griffteruk.kata.socialnetwork.common.Users.SOME_OTHER_EXISTING_USER_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by User on 21/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class FollowCommandShould {

    @Test
    public void returnAnEmptyListAsResult()
    {
        UserRepository userRepository =
                MockUserRepositoryBuilder.aMockUserRepository()
                        .thatFindsUser(
                                MockUserBuilder.aMockUser()
                                        .withName(SOME_EXISTING_USER_NAME)
                                        .build())
                        .thatFindsUser(
                                MockUserBuilder.aMockUser()
                                        .withName(SOME_OTHER_EXISTING_USER_NAME)
                                        .build()
                        ).build();

        assertThat(processFollowCommand(
                userRepository,
                SOME_EXISTING_USER_NAME, SOME_OTHER_EXISTING_USER_NAME),
                is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void followUserWhenUserAndUserToFollowBothExist()
    {
        User user = MockUserBuilder.aMockUser()
                .withName(SOME_EXISTING_USER_NAME)
                .build();

        User userToFollow = MockUserBuilder.aMockUser()
                .withName(SOME_OTHER_EXISTING_USER_NAME)
                .build();

        UserRepository userRepository =
                MockUserRepositoryBuilder.aMockUserRepository()
                    .thatFindsUser(user)
                    .thatFindsUser(userToFollow)
                    .build();

        assertThat(processFollowCommand(
                userRepository,
                SOME_EXISTING_USER_NAME, SOME_OTHER_EXISTING_USER_NAME),
                is(EMPTY_LIST_OF_STRINGS));

        verify(user).addUserToFollow(userToFollow);
    }

    @Test
    public void notFollowUserWhenUserToFollowDoesNotExist()
    {
        User user = MockUserBuilder.aMockUser()
                .withName(SOME_EXISTING_USER_NAME)
                .build();

        UserRepository userRepository =
                MockUserRepositoryBuilder.aMockUserRepository()
                        .thatFindsUser(user)
                        .thatDoesNotFindUserWithName(NON_EXISTENT_USER_NAME)
                        .build();

        assertThat(processFollowCommand(
                userRepository,
                SOME_EXISTING_USER_NAME, NON_EXISTENT_USER_NAME),
                is(EMPTY_LIST_OF_STRINGS));

        verify(user, never()).addUserToFollow(any(User.class));
    }

    private List<String> processFollowCommand(UserRepository userRepository, String userName, String userNameToFollow) {
        FollowCommand followCommand = new FollowCommand(userRepository, userName, userNameToFollow);
        return followCommand.process();
    }
}
