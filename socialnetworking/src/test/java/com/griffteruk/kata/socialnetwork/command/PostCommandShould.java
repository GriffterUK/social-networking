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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by User on 21/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostCommandShould {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();
    private static final String SOME_USER_NAME = "Jim";
    private static final String SOME_USER_MESSAGE = "Hello World!";
    private static final String NEW_USER_NAME = "Alice";
    private static final String NEW_USER_MESSAGE = "I love the weather today!";

    @Mock
    private UserRepository userRepository;

    protected void expectUserRepositoryToFindUserByName(String userName)
    {
        when(userRepository.findUserByName(userName)).thenReturn(Optional.of(new User(userName)));
    }

    protected void expectUserRepositoryNotToFindUserByName(String userName)
    {
        when(userRepository.findUserByName(userName)).thenReturn(Optional.empty());
    }

    protected void expectUserRepositoryToCreateUserForName(String userName)
    {
        when(userRepository.createUser(userName)).thenReturn(true);
    }

    @Test
    public void returnAnEmptyListAsResult()
    {
        expectUserRepositoryToFindUserByName(SOME_USER_NAME);
    
        PostCommand postCommand = new PostCommand(userRepository, SOME_USER_NAME, SOME_USER_MESSAGE);
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

}
