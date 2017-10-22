package com.griffteruk.kata.socialnetwork.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by User on 22/10/2017.
 */
public class SocialUserRepositoryShould {

    private static final String NEW_USER_NAME = "Jim";
    private static final String NON_EXISTENT_USER_NAME = "MrInvisible";

    private SocialUserRepository userRepository;

    @Before
    public void initialise()
    {
        userRepository = new SocialUserRepository();
    }

    @Test
    public void returnUserWhenNewCreatingNewUser()
    {
        assertThat(userRepository.createUser(NEW_USER_NAME), is(instanceOf(User.class)));
    }

    @Test
    public void notFindNonExistentUser()
    {
        assertThat(userRepository.findUserByName(NON_EXISTENT_USER_NAME), is(Optional.empty()));
    }

    @Test
    public void findUserThatHasBeenCreated()
    {
        User newUser = userRepository.createUser(NEW_USER_NAME);
        Optional<User> foundUser = userRepository.findUserByName(NEW_USER_NAME);

        assertThat(foundUser.isPresent(), is(true));
        assertThat(foundUser.get(), is(newUser));
    }
}
