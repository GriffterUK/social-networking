package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.griffteruk.kata.socialnetwork.common.Lists.*;
import static com.griffteruk.kata.socialnetwork.common.Posts.*;
import static com.griffteruk.kata.socialnetwork.common.Users.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by User on 22/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ReadCommandShould  {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    @Test
    public void returnAnEmptyListAsResultWhenUserDoesNotExist()
    {
        assertThat(processReadCommandFor(
                MockUserRepository.aMockUserRepository()
                        .thatDoesNotFindUserName(NON_EXISTENT_USER_NAME)
                        .build(),
                NON_EXISTENT_USER_NAME),
                is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void returnThePostsOfTheRequestingUser()
    {
        User user = MockUserBuilder.aMockUser()
                .withName(SOME_EXISTING_USER_NAME)
                .withPosts(
                        MockPostBuilder.aMockPost()
                                .withMessage(FIRST_POST_OF_EXISTING_USER)
                                .withTimestamp(LocalDateTime.now())
                                .build(),
                        MockPostBuilder.aMockPost()
                                .withMessage(SECOND_POST_OF_EXISTING_USER)
                                .withTimestamp(LocalDateTime.now())
                                .build()
                )
                .build();

        List<String> resultOfReadCommand = processReadCommandFor(
                MockUserRepository.aMockUserRepository()
                        .thatFindsUser(user)
                        .build(),
                SOME_EXISTING_USER_NAME);

        assertTrue(stringListHasStringStartingWith(resultOfReadCommand, FIRST_POST_OF_EXISTING_USER));
        assertTrue(stringListHasStringStartingWith(resultOfReadCommand, SECOND_POST_OF_EXISTING_USER));
    }

    private List<String> processReadCommandFor(UserRepository userRepository, String userName) {
        ReadCommand readCommand = new ReadCommand(userRepository, userName);
        return readCommand.process();
    }
}
