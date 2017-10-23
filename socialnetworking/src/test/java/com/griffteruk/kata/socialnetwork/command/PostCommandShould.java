package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.domain.WithMockedUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by User on 21/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostCommandShould extends WithMockedUserRepository {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    private static final String SOME_EXISTING_USER_POST = "What a wonderful day!";
    private static final String EMPTY_POST = "";

    @Test
    public void returnAnEmptyListAsResult()
    {
        expectUserRepositoryToFindUserByName(SOME_EXISTING_USER_NAME);

        assertThat(processPostCommand(SOME_EXISTING_USER_NAME, SOME_EXISTING_USER_POST),
                is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void createNewUserWhenNewUserCreatesTheirFirstPost()
    {
        expectUserRepositoryToCreateUserForName(NEW_USER_NAME);

        processPostCommand(NEW_USER_NAME, EMPTY_POST);

        verify(userRepository).createUser(NEW_USER_NAME);
    }

    @Test
    public void notCreateNewUserWhenExistingUserCreatesAPost()
    {
        expectUserRepositoryToFindUserByName(SOME_EXISTING_USER_NAME);

        processPostCommand(SOME_EXISTING_USER_NAME, EMPTY_POST);

        verify(userRepository, never()).createUser(SOME_EXISTING_USER_NAME);
    }

    @Test
    public void addPostsToUsersListOfPosts()
    {
        User someUser = expectUserRepositoryToFindUserByName(SOME_EXISTING_USER_NAME);

        processPostCommand(SOME_EXISTING_USER_NAME, SOME_EXISTING_USER_POST );

        verify(someUser).addPost(any(Post.class));
    }

    private List<String> processPostCommand(String userName, String userPost) {
        PostCommand postCommand = new PostCommand(userRepository, userName, userPost);
        return postCommand.process();
    }


}
