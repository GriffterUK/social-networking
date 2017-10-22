package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.domain.WithMockedUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by User on 22/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ReadCommandShould extends WithMockedUserRepository {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    private static String NON_EXISTENT_USER = "MrInvisible";
    private static String SOME_USER_NAME = "Jim";
    private static String SOME_USER_POST = "Hello World!";

    @Test
    public void returnAnEmptyListAsResultWhenUserDoesNotExist()
    {
        expectUserRepositoryNotToFindUserByName(NON_EXISTENT_USER);

        assertThat(new ReadCommand(userRepository, NON_EXISTENT_USER).process(),
                is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void returnThePostsOfAnExistingUser()
    {
        List<String> userPosts = new ArrayList<>();
        userPosts.add(SOME_USER_POST);

        User user = expectUserRepositoryToFindUserByName(SOME_USER_NAME);
        when(user.getPosts()).thenReturn(userPosts);

        assertThat(new ReadCommand(userRepository, SOME_USER_NAME).process(),
                is(userPosts));
    }
}
