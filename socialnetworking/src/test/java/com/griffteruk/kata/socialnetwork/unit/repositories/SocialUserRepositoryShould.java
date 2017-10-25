package com.griffteruk.kata.socialnetwork.unit.repositories;

import com.griffteruk.kata.socialnetwork.unit.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.griffteruk.kata.socialnetwork.unit.common.Users.ANOTHER_NEW_USER_NAME;
import static com.griffteruk.kata.socialnetwork.unit.common.Users.NEW_USER_NAME;
import static com.griffteruk.kata.socialnetwork.unit.common.Users.NON_EXISTENT_USER_NAME;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by User on 22/10/2017.
 */
public class SocialUserRepositoryShould {

    private SocialUserRepository userRepository;

    @Before
    public void initialise()
    {
        userRepository = new SocialUserRepository();
    }

    @Test
    public void returnUserWhenCreatingNewUser()
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

    @Test
    public void findUserAmongMultipleExistingUsers()
    {
        userRepository.createUser(NEW_USER_NAME);
        User userToFind = userRepository.createUser(ANOTHER_NEW_USER_NAME);

        Optional<User> foundUser = userRepository.findUserByName(ANOTHER_NEW_USER_NAME);

        assertThat(foundUser.isPresent(), is(true));
        assertThat(foundUser.get(), is(userToFind));
    }

    @Test
    public void notCreateDuplicateUsers()
    {
        User newUser = userRepository.createUser(NEW_USER_NAME);
        User theSameUser = userRepository.createUser(NEW_USER_NAME);

        assertThat(newUser, is(theSameUser));
    }
}
