package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.*;
import com.griffteruk.kata.socialnetwork.repositories.MockUserRepositoryBuilder;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;

import static com.griffteruk.kata.socialnetwork.common.Lists.EMPTY_LIST_OF_STRINGS;
import static com.griffteruk.kata.socialnetwork.common.Lists.stringListHasStringStartingWith;
import static com.griffteruk.kata.socialnetwork.common.Posts.FIRST_POST_OF_EXISTING_USER;
import static com.griffteruk.kata.socialnetwork.common.Posts.SECOND_POST_OF_EXISTING_USER;
import static com.griffteruk.kata.socialnetwork.common.Users.NON_EXISTENT_USER_NAME;
import static com.griffteruk.kata.socialnetwork.common.Users.SOME_EXISTING_USER_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by User on 22/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ReadCommandShould  {

    @Test
    public void returnAnEmptyListAsResultWhenUserDoesNotExist()
    {
        assertThat(processReadCommandFor(
                MockUserRepositoryBuilder.aMockUserRepository()
                        .thatDoesNotFindUserWithName(NON_EXISTENT_USER_NAME)
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
                MockUserRepositoryBuilder.aMockUserRepository()
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
