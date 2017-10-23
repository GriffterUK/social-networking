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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by User on 22/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ReadCommandShould extends WithMockedUserRepository {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    private static final String FIRST_POST_OF_EXISTING_USER = "Ah, life is great, isn't it!?";
    private static final String SECOND_POST_OF_EXISTING_USER = "Mockito, EasyMock, PowerMock, decisions decisions!";

    @Test
    public void returnAnEmptyListAsResultWhenUserDoesNotExist()
    {
        expectUserRepositoryNotToFindUserByName(NON_EXISTENT_USER_NAME);

        assertThat(processReadCommandFor(NON_EXISTENT_USER_NAME),
                is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void returnThePostsOfTheRequestingUser()
    {
        List<Post> userPosts = new ArrayList<>();
        userPosts.add(mockedPostWithMessage(FIRST_POST_OF_EXISTING_USER));
        userPosts.add(mockedPostWithMessage(SECOND_POST_OF_EXISTING_USER));

        User user = expectUserRepositoryToFindUserByName(SOME_EXISTING_USER_NAME);
        when(user.getPosts()).thenReturn(userPosts);

        List<String> resultOfReadCommand = processReadCommandFor(SOME_EXISTING_USER_NAME);

        assertTrue(listOfStringsContainsStringThatStartsWith(resultOfReadCommand, FIRST_POST_OF_EXISTING_USER));
        assertTrue(listOfStringsContainsStringThatStartsWith(resultOfReadCommand, SECOND_POST_OF_EXISTING_USER));
    }

    private List<String> processReadCommandFor(String userName) {
        ReadCommand readCommand = new ReadCommand(userRepository, userName);
        return readCommand.process();
    }

    private boolean listOfStringsContainsStringThatStartsWith(List<String> listOfStrings, String stringToStartWith)
    {
        return listOfStrings.stream().anyMatch(item -> item.startsWith(stringToStartWith));
    }

    private Post mockedPostWithMessage(String message)
    {
        Post post = mock(Post.class);
        when(post.getMessage()).thenReturn(message);
        return post;
    }
}
