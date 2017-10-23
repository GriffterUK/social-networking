package com.griffteruk.kata.socialnetwork.command;


import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.domain.WithMockedUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WallCommandShould extends WithMockedUserRepository {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    private static final String FIRST_POST_OF_EXISTING_USER = "Ah, life is great, isn't it!?";
    private static final String SECOND_POST_OF_EXISTING_USER = "Mockito, EasyMock, PowerMock, decisions decisions!";

    private static final String FIRST_POST_OF_FOLLOWED_USER = "Hello World!";
    private static final String SECOND_POST_OF_FOLLOWED_USER = "Is anybody out there?";

    @Test
    public void returnAnEmptyListAsResultWhenUserDoesNotExist()
    {
        expectUserRepositoryNotToFindUserByName(NON_EXISTENT_USER_NAME);

        assertThat(processWallCommandFor(NON_EXISTENT_USER_NAME),
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
        when(user.getFollowedUsers()).thenReturn(new ArrayList<User>());

        List<String> resultOfWallCommand = processWallCommandFor(SOME_EXISTING_USER_NAME);

        assertTrue(listOfStringsContainsStringThatStartsWith(resultOfWallCommand, FIRST_POST_OF_EXISTING_USER));
        assertTrue(listOfStringsContainsStringThatStartsWith(resultOfWallCommand, SECOND_POST_OF_EXISTING_USER));
    }

    @Test
    public void returnThePostsOfTheRequestingUserAndUsersTheyFollow()
    {
        List<Post> userPosts = new ArrayList<>();
        userPosts.add(mockedPostWithMessage(FIRST_POST_OF_EXISTING_USER));
        userPosts.add(mockedPostWithMessage(SECOND_POST_OF_EXISTING_USER));

        List<Post> followedUserPosts = new ArrayList<>();
        followedUserPosts.add(mockedPostWithMessage(FIRST_POST_OF_FOLLOWED_USER));
        followedUserPosts.add(mockedPostWithMessage(SECOND_POST_OF_FOLLOWED_USER));

        User followedUser = mock(User.class);
        when(followedUser.getName()).thenReturn("Alice");
        when(followedUser.getPosts()).thenReturn(followedUserPosts);

        List<User> followedUsers = new ArrayList<>();
        followedUsers.add(followedUser);

        User user = mock(User.class);
        when(user.getName()).thenReturn(SOME_EXISTING_USER_NAME);
        when(user.getPosts()).thenReturn(userPosts);
        when(user.getFollowedUsers()).thenReturn(followedUsers);

        expectUserRepositoryToFindThisUser(user);
        expectUserRepositoryToFindThisUser(followedUser);

        List<String> resultOfWallCommand = processWallCommandFor(SOME_EXISTING_USER_NAME);

        assertTrue(listOfStringsContainsStringThatStartsWith(resultOfWallCommand, FIRST_POST_OF_EXISTING_USER));
        assertTrue(listOfStringsContainsStringThatStartsWith(resultOfWallCommand, SECOND_POST_OF_EXISTING_USER));
    }


    private List<String> processWallCommandFor(String userName) {
        WallCommand wallCommand = new WallCommand(userRepository, userName);
        return wallCommand.process();
    }

    private boolean listOfStringsContainsStringThatStartsWith(List<String> listOfStrings, String stringToStartWith)
    {
        return listOfStrings.stream().anyMatch(item -> item.startsWith(stringToStartWith));
    }

    private Post mockedPostWithMessage(String message)
    {
        Post post = mock(Post.class);
        when(post.getMessage()).thenReturn(message);
        when(post.getTimestamp()).thenReturn(LocalDateTime.now());
        return post;
    }

}
