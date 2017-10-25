package com.griffteruk.kata.socialnetwork.unit.command;

import com.griffteruk.kata.socialnetwork.command.FollowCommand;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;
import com.griffteruk.kata.socialnetwork.unit.domain.MockUserBuilder;
import com.griffteruk.kata.socialnetwork.unit.repositories.MockUserRepositoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.griffteruk.kata.socialnetwork.unit.common.Lists.EMPTY_LIST_OF_STRINGS;
import static com.griffteruk.kata.socialnetwork.unit.common.Users.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Lee Griffiths on 21/10/2017.
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

        List<String> followCommandResult =
                processFollowCommand(userRepository, SOME_EXISTING_USER_NAME, SOME_OTHER_EXISTING_USER_NAME);

        assertThat(followCommandResult, is(EMPTY_LIST_OF_STRINGS));
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

        List<String> followCommandResult =
                processFollowCommand(userRepository, SOME_EXISTING_USER_NAME, SOME_OTHER_EXISTING_USER_NAME);

        assertThat(followCommandResult, is(EMPTY_LIST_OF_STRINGS));

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

        List<String> followCommandResult =
                processFollowCommand( userRepository, SOME_EXISTING_USER_NAME, NON_EXISTENT_USER_NAME);

        assertThat(followCommandResult, is(EMPTY_LIST_OF_STRINGS));

        verify(user, never()).addUserToFollow(any(User.class));
    }

    private List<String> processFollowCommand(UserRepository userRepository, String userName, String userNameToFollow) {
        FollowCommand followCommand = new FollowCommand(userRepository, userName, userNameToFollow);
        return followCommand.process();
    }
}
