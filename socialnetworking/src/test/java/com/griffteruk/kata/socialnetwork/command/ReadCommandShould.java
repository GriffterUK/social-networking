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

    private static final String SOME_EXISTING_USER_POST = "Ah, life is great, isn't it!?";

    @Test
    public void returnAnEmptyListAsResultWhenUserDoesNotExist()
    {
        expectUserRepositoryNotToFindUserByName(NON_EXISTENT_USER_NAME);

        assertThat(processReadCommand(NON_EXISTENT_USER_NAME),
                is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void returnThePostsOfAnExistingUser()
    {
        List<String> userPosts = new ArrayList<>();
        userPosts.add(SOME_EXISTING_USER_POST);

        User user = expectUserRepositoryToFindUserByName(SOME_EXISTING_USER_NAME);
        when(user.getPosts()).thenReturn(userPosts);

        assertThat(processReadCommand(SOME_EXISTING_USER_NAME),
                is(userPosts));
    }

    private List<String> processReadCommand(String userName) {
        ReadCommand readCommand = new ReadCommand(userRepository, userName);
        return readCommand.process();
    }


}
