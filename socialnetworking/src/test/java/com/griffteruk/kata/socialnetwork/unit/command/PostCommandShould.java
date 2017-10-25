package com.griffteruk.kata.socialnetwork.unit.command;

import com.griffteruk.kata.socialnetwork.command.PostCommand;
import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.PostFactory;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.griffteruk.kata.socialnetwork.unit.common.Lists.EMPTY_LIST_OF_STRINGS;
import static com.griffteruk.kata.socialnetwork.unit.common.Posts.FIRST_POST_OF_EXISTING_USER;
import static com.griffteruk.kata.socialnetwork.unit.common.Posts.FIRST_POST_OF_NEW_USER;
import static com.griffteruk.kata.socialnetwork.unit.common.Users.NEW_USER_NAME;
import static com.griffteruk.kata.socialnetwork.unit.common.Users.SOME_EXISTING_USER_NAME;
import static com.griffteruk.kata.socialnetwork.unit.domain.MockUserBuilder.aMockUser;
import static com.griffteruk.kata.socialnetwork.unit.repositories.MockUserRepositoryBuilder.aMockUserRepository;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Lee Griffiths on 21/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostCommandShould {

    @Test
    public void returnAnEmptyListAsResult() {
        UserRepository userRepository =
                aMockUserRepository()
                        .thatFindsUser(
                                aMockUser()
                                        .withName(SOME_EXISTING_USER_NAME)
                                        .build()
                        ).build();

        assertThat(
                processPostCommand(userRepository, SOME_EXISTING_USER_NAME, FIRST_POST_OF_EXISTING_USER),
                is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void createNewUserWhenNewUserCreatesTheirFirstPost() {
        UserRepository userRepository =
                aMockUserRepository()
                        .thatDoesNotFindUserWithName(NEW_USER_NAME)
                        .thatCreatesUser(
                                aMockUser()
                                        .withName(NEW_USER_NAME)
                                        .build()
                        ).build();

        assertThat(
                processPostCommand(userRepository, NEW_USER_NAME, FIRST_POST_OF_NEW_USER),
                is(EMPTY_LIST_OF_STRINGS));

        verify(userRepository).createUser(NEW_USER_NAME);
    }

    @Test
    public void notCreateNewUserWhenExistingUserCreatesAPost() {
        UserRepository userRepository =
                aMockUserRepository()
                        .thatFindsUser(
                                aMockUser()
                                        .withName(SOME_EXISTING_USER_NAME)
                                        .build()
                        ).build();

        processPostCommand(userRepository, SOME_EXISTING_USER_NAME, FIRST_POST_OF_EXISTING_USER);

        verify(userRepository, never()).createUser(SOME_EXISTING_USER_NAME);
    }

    @Test
    public void addPostsToUsersListOfPosts() {
        User someUser = aMockUser()
                .withName(SOME_EXISTING_USER_NAME)
                .build();

        UserRepository userRepository =
                aMockUserRepository()
                        .thatFindsUser(someUser)
                        .build();

        processPostCommand(userRepository, SOME_EXISTING_USER_NAME, FIRST_POST_OF_EXISTING_USER);

        verify(someUser).addPost(any(Post.class));
    }

    private List<String> processPostCommand(UserRepository userRepository, String userName, String userPost) {
        PostCommand postCommand = new PostCommand(userRepository, mock(PostFactory.class), userName, userPost);
        return postCommand.process();
    }
}
