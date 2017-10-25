package com.griffteruk.kata.socialnetwork.unit.command;

import com.griffteruk.kata.socialnetwork.command.WallCommand;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;

import static com.griffteruk.kata.socialnetwork.unit.common.Lists.EMPTY_LIST_OF_STRINGS;
import static com.griffteruk.kata.socialnetwork.unit.common.Posts.*;
import static com.griffteruk.kata.socialnetwork.unit.common.Users.*;
import static com.griffteruk.kata.socialnetwork.unit.domain.MockPostBuilder.aMockPost;
import static com.griffteruk.kata.socialnetwork.unit.domain.MockUserBuilder.aMockUser;
import static com.griffteruk.kata.socialnetwork.unit.repositories.MockUserRepositoryBuilder.aMockUserRepository;
import static com.griffteruk.test.matchers.StringListMatchers.containsInOrderStringsStartingWith;
import static com.griffteruk.test.matchers.StringListMatchers.containsStringsStartingWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class WallCommandShould {

    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final LocalDateTime TWO_SECONDS_LATER = NOW.minusSeconds(-2);
    private static final LocalDateTime FOUR_MINUTES_LATER = NOW.minusMinutes(-4);
    private static final LocalDateTime SEVEN_DAYS_LATER = NOW.minusDays(-7);

    @Test
    public void returnEmptyListForNonExistingUser() {
        assertThat(processWallCommandFor(
                aMockUserRepository()
                        .thatDoesNotFindUserWithName(NON_EXISTENT_USER_NAME)
                        .build(),
                NON_EXISTENT_USER_NAME),
                is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void returnTheUsersPostsWhenTheyFollowNobody() {
        User user = aMockUser()
                .withName(SOME_EXISTING_USER_NAME)
                .withPosts(
                        aMockPost()
                                .withMessage(FIRST_POST_OF_EXISTING_USER)
                                .withTimestamp(NOW)
                                .build(),
                        aMockPost()
                                .withMessage(SECOND_POST_OF_EXISTING_USER)
                                .withTimestamp(TWO_SECONDS_LATER)
                                .build()
                )
                .build();

        List<String> resultOfWallCommand = processWallCommandFor(
                aMockUserRepository()
                        .thatFindsUser(user)
                        .build(),
                SOME_EXISTING_USER_NAME);

        assertThat(resultOfWallCommand,
                containsInOrderStringsStartingWith(
                        userPostMessage(SOME_EXISTING_USER_NAME, SECOND_POST_OF_EXISTING_USER),
                        userPostMessage(SOME_EXISTING_USER_NAME, FIRST_POST_OF_EXISTING_USER)
                ));
    }

    @Test
    public void returnTheUsersPostsAndThePostsOfTheUsersTheyFollow() {
        User followedUser = aMockUser()
                .withName(FOLLOWED_USER_NAME)
                .withPosts(
                        aMockPost()
                                .withMessage(FIRST_POST_OF_FOLLOWED_USER)
                                .withTimestamp(NOW)
                                .build(),
                        aMockPost()
                                .withMessage(SECOND_POST_OF_FOLLOWED_USER)
                                .withTimestamp(NOW)
                                .build()
                )
                .build();

        User user = aMockUser()
                .withName(SOME_EXISTING_USER_NAME)
                .followingUser(followedUser)
                .withPosts(
                        aMockPost()
                                .withMessage(FIRST_POST_OF_EXISTING_USER)
                                .withTimestamp(NOW)
                                .build(),
                        aMockPost()
                                .withMessage(SECOND_POST_OF_EXISTING_USER)
                                .withTimestamp(NOW)
                                .build()
                )
                .build();

        List<String> resultOfWallCommand = processWallCommandFor(
                aMockUserRepository()
                        .thatFindsUser(user)
                        .thatFindsUser(followedUser)
                        .build(),
                SOME_EXISTING_USER_NAME);

        assertThat(resultOfWallCommand,
                containsStringsStartingWith(
                        userPostMessage(SOME_EXISTING_USER_NAME, FIRST_POST_OF_EXISTING_USER),
                        userPostMessage(SOME_EXISTING_USER_NAME, SECOND_POST_OF_EXISTING_USER),
                        userPostMessage(FOLLOWED_USER_NAME, FIRST_POST_OF_FOLLOWED_USER),
                        userPostMessage(FOLLOWED_USER_NAME, SECOND_POST_OF_FOLLOWED_USER)
                ));
    }

    @Test
    public void returnAllPostsInReverseChronologicalOrder() {
        User followedUser = aMockUser()
                .withName(FOLLOWED_USER_NAME)
                .withPosts(
                        aMockPost()
                                .withMessage(FIRST_POST_OF_FOLLOWED_USER)
                                .withTimestamp(TWO_SECONDS_LATER)
                                .build(),
                        aMockPost()
                                .withMessage(SECOND_POST_OF_FOLLOWED_USER)
                                .withTimestamp(SEVEN_DAYS_LATER)
                                .build()
                )
                .build();

        User user = aMockUser()
                .withName(SOME_EXISTING_USER_NAME)
                .followingUser(followedUser)
                .withPosts(
                        aMockPost()
                                .withMessage(FIRST_POST_OF_EXISTING_USER)
                                .withTimestamp(NOW)
                                .build(),
                        aMockPost()
                                .withMessage(SECOND_POST_OF_EXISTING_USER)
                                .withTimestamp(FOUR_MINUTES_LATER)
                                .build()
                )
                .build();

        List<String> resultOfWallCommand = processWallCommandFor(
                aMockUserRepository()
                        .thatFindsUser(user)
                        .thatFindsUser(followedUser)
                        .build(),
                SOME_EXISTING_USER_NAME);

        assertThat(resultOfWallCommand,
                containsInOrderStringsStartingWith(
                        userPostMessage(FOLLOWED_USER_NAME, SECOND_POST_OF_FOLLOWED_USER),
                        userPostMessage(SOME_EXISTING_USER_NAME, SECOND_POST_OF_EXISTING_USER),
                        userPostMessage(FOLLOWED_USER_NAME, FIRST_POST_OF_FOLLOWED_USER),
                        userPostMessage(SOME_EXISTING_USER_NAME, FIRST_POST_OF_EXISTING_USER)
                ));
    }

    private List<String> processWallCommandFor(UserRepository userRepository, String userName) {
        WallCommand wallCommand = new WallCommand(userRepository, userName);
        return wallCommand.process();
    }

    private String userPostMessage(String user, String message) {
        return user + " : " + message;
    }
}
