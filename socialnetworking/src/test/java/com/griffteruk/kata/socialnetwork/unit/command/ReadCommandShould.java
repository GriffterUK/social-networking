package com.griffteruk.kata.socialnetwork.unit.command;

import com.griffteruk.kata.socialnetwork.command.ReadCommand;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;
import com.griffteruk.kata.socialnetwork.unit.domain.MockPostBuilder;
import com.griffteruk.kata.socialnetwork.unit.domain.MockUserBuilder;
import com.griffteruk.kata.socialnetwork.unit.repositories.MockUserRepositoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;

import static com.griffteruk.kata.socialnetwork.unit.common.Lists.EMPTY_LIST_OF_STRINGS;
import static com.griffteruk.kata.socialnetwork.unit.common.Lists.stringListHasStringStartingWith;
import static com.griffteruk.kata.socialnetwork.unit.common.Posts.FIRST_POST_OF_EXISTING_USER;
import static com.griffteruk.kata.socialnetwork.unit.common.Posts.SECOND_POST_OF_EXISTING_USER;
import static com.griffteruk.kata.socialnetwork.unit.common.Users.NON_EXISTENT_USER_NAME;
import static com.griffteruk.kata.socialnetwork.unit.common.Users.SOME_EXISTING_USER_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lee Griffiths on 22/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ReadCommandShould  {

    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final LocalDateTime ONE_SECOND_LATER = NOW.minusSeconds(-1);

    @Test
    public void returnEmptyListForNonExistingUser()
    {
        assertThat(processReadCommandFor(
                MockUserRepositoryBuilder.aMockUserRepository()
                        .thatDoesNotFindUserWithName(NON_EXISTENT_USER_NAME)
                        .build(),
                NON_EXISTENT_USER_NAME),
                is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void returnTheUsersPostsAsMessages()
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

        assertThat(resultOfReadCommand.size(), is(2));
        assertTrue(stringListHasStringStartingWith(resultOfReadCommand, FIRST_POST_OF_EXISTING_USER));
        assertTrue(stringListHasStringStartingWith(resultOfReadCommand, SECOND_POST_OF_EXISTING_USER));
    }

    @Test
    public void returnTheUsersPostsInReverseChronologicalOrder()
    {
        User user = MockUserBuilder.aMockUser()
                .withName(SOME_EXISTING_USER_NAME)
                .withPosts(
                        MockPostBuilder.aMockPost()
                                .withMessage(SECOND_POST_OF_EXISTING_USER)
                                .withTimestamp(ONE_SECOND_LATER)
                                .build(),
                        MockPostBuilder.aMockPost()
                                .withMessage(FIRST_POST_OF_EXISTING_USER)
                                .withTimestamp(NOW)
                                .build()

                )
                .build();

        List<String> resultOfReadCommand = processReadCommandFor(
                MockUserRepositoryBuilder.aMockUserRepository()
                        .thatFindsUser(user)
                        .build(),
                SOME_EXISTING_USER_NAME);

        assertThat(resultOfReadCommand.size(), is(2));
        assertTrue(resultOfReadCommand.get(0).startsWith(SECOND_POST_OF_EXISTING_USER));
        assertTrue(resultOfReadCommand.get(1).startsWith(FIRST_POST_OF_EXISTING_USER));
    }

    private List<String> processReadCommandFor(UserRepository userRepository, String userName) {
        ReadCommand readCommand = new ReadCommand(userRepository, userName);
        return readCommand.process();
    }
}
