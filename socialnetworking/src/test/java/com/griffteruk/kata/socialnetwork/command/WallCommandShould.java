package com.griffteruk.kata.socialnetwork.command;


import com.griffteruk.kata.socialnetwork.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;

import static com.griffteruk.kata.socialnetwork.common.Lists.*;
import static com.griffteruk.kata.socialnetwork.common.Posts.*;
import static com.griffteruk.kata.socialnetwork.common.Users.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class WallCommandShould {

    @Test
    public void returnAnEmptyListAsResultWhenUserDoesNotExist()
    {
        assertThat(processWallCommandFor(
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

        List<String> resultOfWallCommand = processWallCommandFor(
                MockUserRepository.aMockUserRepository()
                    .thatFindsUser(user)
                    .build(),
                SOME_EXISTING_USER_NAME);

        assertTrue(stringListHasStringStartingWith(resultOfWallCommand, FIRST_POST_OF_EXISTING_USER));
        assertTrue(stringListHasStringStartingWith(resultOfWallCommand, SECOND_POST_OF_EXISTING_USER));
    }

    @Test
    public void returnThePostsOfTheRequestingUserAndUsersTheyFollow()
    {
        User followedUser = MockUserBuilder.aMockUser()
                .withName("Alice")
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
                MockUserRepository.aMockUserRepository()
                        .thatFindsUser(user)
                        .thatFindsUser(followedUser)
                        .build(),
                SOME_EXISTING_USER_NAME);

        assertTrue(stringListHasStringStartingWith(resultOfWallCommand, FIRST_POST_OF_EXISTING_USER));
        assertTrue(stringListHasStringStartingWith(resultOfWallCommand, SECOND_POST_OF_EXISTING_USER));
        assertTrue(stringListHasStringStartingWith(resultOfWallCommand, FIRST_POST_OF_FOLLOWED_USER));
        assertTrue(stringListHasStringStartingWith(resultOfWallCommand, SECOND_POST_OF_FOLLOWED_USER));
    }

    private List<String> processWallCommandFor(UserRepository userRepository, String userName) {
        WallCommand wallCommand = new WallCommand(userRepository, userName);
        return wallCommand.process();
    }
}
