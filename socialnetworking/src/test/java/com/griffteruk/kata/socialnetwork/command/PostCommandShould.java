package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.domain.UserRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by User on 21/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostCommandShould {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    private static final String SOME_EXISTING_USER_NAME = "Jim";
    private static final String SOME_EXISTING_USER_MESSAGE = "Hello World!";

    private static final String NEW_USER_NAME = "Alice";
    private static final String NEW_USER_MESSAGE = "I love the weather today!";

    @Mock
    private UserRepository userRepository;

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
        expectUserRepositoryNotToFindUserByName(NEW_USER_NAME);
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
    

    protected User expectUserRepositoryToFindUserByName(String userName)
    {
        User user = mock(User.class);
        when(user.getName()).thenReturn(userName);

        when(userRepository.findUserByName(userName)).thenReturn(Optional.of(user));
        return user;
    }

    protected void expectUserRepositoryNotToFindUserByName(String userName)
    {
        when(userRepository.findUserByName(userName)).thenReturn(Optional.empty());
    }

    protected User expectUserRepositoryToCreateUserForName(String userName)
    {
        User user = mock(User.class);
        when(user.getName()).thenReturn(userName);

        when(userRepository.createUser(userName)).
                thenReturn(user);

        when(userRepository.findUserByName(userName)).
                thenReturn(Optional.empty()).
                thenReturn(Optional.of(user));

        return user;
    }
}
