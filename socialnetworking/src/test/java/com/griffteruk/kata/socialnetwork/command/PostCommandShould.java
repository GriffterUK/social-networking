package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.griffteruk.kata.socialnetwork.common.Lists.EMPTY_LIST_OF_STRINGS;
import static com.griffteruk.kata.socialnetwork.common.Posts.FIRST_POST_OF_EXISTING_USER;
import static com.griffteruk.kata.socialnetwork.common.Posts.FIRST_POST_OF_NEW_USER;
import static com.griffteruk.kata.socialnetwork.common.Users.NEW_USER_NAME;
import static com.griffteruk.kata.socialnetwork.common.Users.SOME_EXISTING_USER_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by User on 21/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostCommandShould  {

    @Test
    public void returnAnEmptyListAsResult()
    {
        UserRepository userRepository =
                MockUserRepositoryBuilder.aMockUserRepository()
                    .thatFindsUser(
                            MockUserBuilder.aMockUser()
                                    .withName(SOME_EXISTING_USER_NAME)
                                    .build()
                    ).build();

        assertThat(
                processPostCommand(userRepository, SOME_EXISTING_USER_NAME, FIRST_POST_OF_EXISTING_USER),
                is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void createNewUserWhenNewUserCreatesTheirFirstPost()
    {
        UserRepository userRepository =
                MockUserRepositoryBuilder.aMockUserRepository()
                    .thatDoesNotFindUserWithName(NEW_USER_NAME)
                    .thatCreatesUser(
                            MockUserBuilder.aMockUser()
                                    .withName(NEW_USER_NAME)
                                    .build()
                    ).build();

        assertThat(
                processPostCommand(userRepository, NEW_USER_NAME, FIRST_POST_OF_NEW_USER),
                is(EMPTY_LIST_OF_STRINGS));

        verify(userRepository).createUser(NEW_USER_NAME);
    }

    @Test
    public void notCreateNewUserWhenExistingUserCreatesAPost()
    {
        UserRepository userRepository =
                MockUserRepositoryBuilder.aMockUserRepository()
                        .thatFindsUser(
                                MockUserBuilder.aMockUser()
                                        .withName(SOME_EXISTING_USER_NAME)
                                        .build()
                        ).build();

        processPostCommand(userRepository, SOME_EXISTING_USER_NAME, FIRST_POST_OF_EXISTING_USER);

        verify(userRepository, never()).createUser(SOME_EXISTING_USER_NAME);
    }

    @Test
    public void addPostsToUsersListOfPosts()
    {
        User someUser = MockUserBuilder.aMockUser()
                .withName(SOME_EXISTING_USER_NAME)
                .build();

        UserRepository userRepository =
                MockUserRepositoryBuilder.aMockUserRepository()
                        .thatFindsUser(someUser)
                        .build();

        processPostCommand(userRepository, SOME_EXISTING_USER_NAME, FIRST_POST_OF_EXISTING_USER );

        verify(someUser).addPost(any(Post.class));
    }

    private List<String> processPostCommand(UserRepository userRepository, String userName, String userPost) {
        PostCommand postCommand = new PostCommand(userRepository, userName, userPost);
        return postCommand.process();
    }
}
