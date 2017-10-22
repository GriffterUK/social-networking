package com.griffteruk.kata.socialnetwork.domain;

import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by User on 22/10/2017.
 */
public abstract class WithMockedUserRepository {

    @Mock
    protected UserRepository userRepository;

    protected static final String SOME_EXISTING_USER_NAME = "Jim";
    protected static final String SOME_OTHER_EXISTING_USER_NAME = "Bob";
    protected static final String NEW_USER_NAME = "Alice";
    protected static final String NON_EXISTENT_USER_NAME = "MrInvisible";

    protected User expectUserRepositoryToFindUserByName(String userName)
    {
        User user = mock(User.class);
        when(user.getName()).thenReturn(userName);

        when(userRepository.findUserByName(userName)).
                thenReturn(Optional.of(user));

        return user;
    }

    protected void expectUserRepositoryNotToFindUserByName(String userName)
    {
        when(userRepository.findUserByName(userName)).
                thenReturn(Optional.empty());
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
