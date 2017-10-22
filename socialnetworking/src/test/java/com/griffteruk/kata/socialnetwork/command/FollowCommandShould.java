package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.UserRepository;
import com.griffteruk.kata.socialnetwork.domain.WithMockedUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by User on 21/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class FollowCommandShould extends WithMockedUserRepository {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    private static final String SOME_EXISTING_USER_NAME = "Jim";
    private static final String SOME_OTHER_EXISTING_USER_NAME = "Bob";

    @Test
    public void returnAnEmptyListAsResult()
    {
        assertThat(new FollowCommand(userRepository, SOME_EXISTING_USER_NAME, SOME_OTHER_EXISTING_USER_NAME).process(),
                is(EMPTY_LIST_OF_STRINGS));
    }
}
