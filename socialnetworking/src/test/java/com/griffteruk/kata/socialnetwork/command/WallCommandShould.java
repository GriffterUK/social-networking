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
import static com.griffteruk.kata.socialnetwork.common.Posts.*;
import static com.griffteruk.kata.socialnetwork.common.Users.FOLLOWED_USER_NAME;
import static com.griffteruk.kata.socialnetwork.common.Users.NON_EXISTENT_USER_NAME;
import static com.griffteruk.kata.socialnetwork.common.Users.SOME_EXISTING_USER_NAME;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class WallCommandShould {

    @Test
    public void returnAnEmptyListAsResultWhenUserDoesNotExist()
    {
        assertThat(processWallCommandFor(
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

        List<String> resultOfWallCommand = processWallCommandFor(
                MockUserRepositoryBuilder.aMockUserRepository()
                    .thatFindsUser(user)
                    .build(),
                SOME_EXISTING_USER_NAME);

        assertTrue(stringListHasStringStartingWith(resultOfWallCommand,
                SOME_EXISTING_USER_NAME + " : " + FIRST_POST_OF_EXISTING_USER));

        assertTrue(stringListHasStringStartingWith(resultOfWallCommand,
                SOME_EXISTING_USER_NAME + " : " + SECOND_POST_OF_EXISTING_USER));
    }

    @Test
    public void returnThePostsOfTheRequestingUserAndUsersTheyFollow()
    {
        User followedUser = MockUserBuilder.aMockUser()
                .withName(FOLLOWED_USER_NAME)
                .withPosts(
                        MockPostBuilder.aMockPost()
                                .withMessage(FIRST_POST_OF_FOLLOWED_USER)
                                .withTimestamp(LocalDateTime.now())
                                .build(),
                        MockPostBuilder.aMockPost()
                                .withMessage(SECOND_POST_OF_FOLLOWED_USER)
                                .withTimestamp(LocalDateTime.now())
                                .build()
                )
                .build();


        User user = MockUserBuilder.aMockUser()
                .withName(SOME_EXISTING_USER_NAME)
                .followingUser(followedUser)
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

        List<String> resultOfWallCommand = processWallCommandFor(
                MockUserRepositoryBuilder.aMockUserRepository()
                        .thatFindsUser(user)
                        .thatFindsUser(followedUser)
                        .build(),
                SOME_EXISTING_USER_NAME);

        assertTrue(stringListHasStringStartingWith(resultOfWallCommand,
                SOME_EXISTING_USER_NAME + " : " + FIRST_POST_OF_EXISTING_USER));

        assertTrue(stringListHasStringStartingWith(resultOfWallCommand,
                SOME_EXISTING_USER_NAME + " : " + SECOND_POST_OF_EXISTING_USER));

        assertTrue(stringListHasStringStartingWith(resultOfWallCommand,
                FOLLOWED_USER_NAME + " : " + FIRST_POST_OF_FOLLOWED_USER));
        assertTrue(stringListHasStringStartingWith(resultOfWallCommand,
                FOLLOWED_USER_NAME + " : " + SECOND_POST_OF_FOLLOWED_USER));
    }

    private List<String> processWallCommandFor(UserRepository userRepository, String userName) {
        WallCommand wallCommand = new WallCommand(userRepository, userName);
        return wallCommand.process();
    }
}
