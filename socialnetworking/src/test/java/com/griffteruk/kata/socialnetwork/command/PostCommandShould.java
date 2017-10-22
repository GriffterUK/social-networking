package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.domain.UserRepository;
import com.griffteruk.kata.socialnetwork.domain.WithMockedUserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by User on 21/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostCommandShould extends WithMockedUserRepository {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    private static final String SOME_EXISTING_USER_NAME = "Jim";
    private static final String SOME_EXISTING_USER_MESSAGE = "Hello World!";

    private static final String NEW_USER_NAME = "Alice";
    private static final String NEW_USER_MESSAGE = "I love the weather today!";

    @Test
    public void returnAnEmptyListAsResult()
    {
        expectUserRepositoryToFindUserByName(SOME_EXISTING_USER_NAME);

        PostCommand postCommand = new PostCommand(userRepository, SOME_EXISTING_USER_NAME, SOME_EXISTING_USER_MESSAGE);
        assertThat(postCommand.process(), is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void createNewUserWhenNewUserCreatesTheirFirstPost()
    {
        expectUserRepositoryToCreateUserForName(NEW_USER_NAME);

        PostCommand postCommand = new PostCommand(userRepository, NEW_USER_NAME, NEW_USER_MESSAGE);
        postCommand.process();

        verify(userRepository).createUser(NEW_USER_NAME);
    }

    @Test
    public void notCreateNewUserWhenExistingUserCreatesAPost()
    {
        expectUserRepositoryToFindUserByName(SOME_EXISTING_USER_NAME);

        PostCommand postCommand = new PostCommand(userRepository, SOME_EXISTING_USER_NAME, SOME_EXISTING_USER_MESSAGE);
        postCommand.process();

        verify(userRepository, never()).createUser(NEW_USER_NAME);
    }

    @Test
    public void addPostsToUsersListOfPosts()
    {
        User someUser = expectUserRepositoryToFindUserByName(SOME_EXISTING_USER_NAME);

        PostCommand postCommand = new PostCommand(userRepository, SOME_EXISTING_USER_NAME, SOME_EXISTING_USER_MESSAGE);
        postCommand.process();

        verify(someUser).addPost(SOME_EXISTING_USER_MESSAGE);
    }



}
