package com.griffteruk.kata.socialnetwork.command;


import com.griffteruk.kata.socialnetwork.domain.MockPostBuilder;
import com.griffteruk.kata.socialnetwork.domain.MockUserBuilder;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.domain.WithMockedUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class WallCommandShould extends WithMockedUserRepository {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    private static final String FIRST_POST_OF_EXISTING_USER = "Ah, life is great, isn't it!?";
    private static final String SECOND_POST_OF_EXISTING_USER = "Mockito, EasyMock, PowerMock, decisions decisions!";

    private static final String FIRST_POST_OF_FOLLOWED_USER = "Hello World!";
    private static final String SECOND_POST_OF_FOLLOWED_USER = "Is anybody out there?";

    @Test
    public void returnAnEmptyListAsResultWhenUserDoesNotExist()
    {
        expectUserRepositoryNotToFindUserByName(NON_EXISTENT_USER_NAME);

        assertThat(processWallCommandFor(NON_EXISTENT_USER_NAME),
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

        expectUserRepositoryToFindThisUser(user);

        List<String> resultOfWallCommand = processWallCommandFor(SOME_EXISTING_USER_NAME);

        assertTrue(listOfStringsContainsStringThatStartsWith(resultOfWallCommand, FIRST_POST_OF_EXISTING_USER));
        assertTrue(listOfStringsContainsStringThatStartsWith(resultOfWallCommand, SECOND_POST_OF_EXISTING_USER));
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

        expectUserRepositoryToFindThisUser(user);
        expectUserRepositoryToFindThisUser(followedUser);

        List<String> resultOfWallCommand = processWallCommandFor(SOME_EXISTING_USER_NAME);

        assertTrue(listOfStringsContainsStringThatStartsWith(resultOfWallCommand, FIRST_POST_OF_EXISTING_USER));
        assertTrue(listOfStringsContainsStringThatStartsWith(resultOfWallCommand, SECOND_POST_OF_EXISTING_USER));
    }


    private List<String> processWallCommandFor(String userName) {
        WallCommand wallCommand = new WallCommand(userRepository, userName);
        return wallCommand.process();
    }

    private boolean listOfStringsContainsStringThatStartsWith(List<String> listOfStrings, String stringToStartWith)
    {
        return listOfStrings.stream().anyMatch(item -> item.startsWith(stringToStartWith));
    }
}
