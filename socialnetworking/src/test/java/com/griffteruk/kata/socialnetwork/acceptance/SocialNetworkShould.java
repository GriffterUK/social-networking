package com.griffteruk.kata.socialnetwork.acceptance;

import com.griffteruk.kata.socialnetwork.SocialConsole;
import com.griffteruk.kata.socialnetwork.command.SocialCommandFactory;
import com.griffteruk.kata.socialnetwork.command.SocialCommandReader;
import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.PostFactory;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.io.TextConsole;
import com.griffteruk.kata.socialnetwork.repositories.SocialUserRepository;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SocialNetworkShould {

    private static final String NO_TEXT = "";
    private static final int ONE_MILLI_SECOND = 1;

    @Mock
    private TextConsole textConsole;

    private SocialConsole socialConsole;

    private UserRepository userRepository;

    @Before
    public void initialise() {
        PostFactory postFactory =
                new SocialPostWithDelayFactory(ONE_MILLI_SECOND);

        userRepository = new SocialUserRepository();

        socialConsole = new SocialConsole(textConsole,
                new SocialCommandReader(
                        new SocialCommandFactory(
                                userRepository, postFactory)));
    }

    @Test
    public void postUserMessagesToTheirPersonalTimeline() {

        when(textConsole.readLine())
                .thenReturn("Alice -> I love the weather today")
                .thenReturn("Bob -> Damn! We lost!")
                .thenReturn("Bob -> Good game though")
                .thenReturn(NO_TEXT);

        socialConsole.execute();

        assertThat(userWithName("Alice"), hasPostWithMessage("I love the weather today"));
        assertThat(userWithName("Bob"), hasPostWithMessage("Damn! We lost!"));
        assertThat(userWithName("Bob"), hasPostWithMessage("Good game though"));
    }

    @Test
    public void readUserMessagesFromTheirPersonalTimeline() {

        when(textConsole.readLine())
                .thenReturn("Alice -> I love the weather today")
                .thenReturn("Bob -> Damn! We lost!")
                .thenReturn("Bob -> Good game though")
                .thenReturn("Alice")
                .thenReturn("Bob")
                .thenReturn(NO_TEXT);

        socialConsole.execute();

        assertThat(userWithName("Alice"), isNotEmpty());
        assertThat(userWithName("Bob"), isNotEmpty());

        InOrder inOrder = inOrder(textConsole);

        inOrder.verify(textConsole).writeLine(contains("I love the weather today"));
        inOrder.verify(textConsole).writeLine(contains("Good game though"));
        inOrder.verify(textConsole).writeLine(contains("Damn! We lost!"));
    }

    @Test
    public void readAggregatedUserMessagesFromTheirPersonalTimelineAndUsersTheyFollow() {

        when(textConsole.readLine())
                .thenReturn("Alice -> I love the weather today")
                .thenReturn("Bob -> Damn! We lost!")
                .thenReturn("Bob -> Good game though")
                .thenReturn("Charlie -> I'm in New York today! Anyone want a coffee?")
                .thenReturn("Charlie follows Alice")
                .thenReturn("Charlie wall")
                .thenReturn("Charlie follows Bob")
                .thenReturn("Charlie wall")
                .thenReturn(NO_TEXT);

        socialConsole.execute();

        assertThat(userWithName("Alice"), isNotEmpty());
        assertThat(userWithName("Bob"), isNotEmpty());
        assertThat(userWithName("Charlie"), isNotEmpty());

        InOrder inOrder = inOrder(textConsole);

        inOrder.verify(textConsole).writeLine(contains("Charlie : I'm in New York today! Anyone want a coffee?"));
        inOrder.verify(textConsole).writeLine(contains("Alice : I love the weather today"));

        inOrder.verify(textConsole).writeLine(contains("Charlie : I'm in New York today! Anyone want a coffee?"));
        inOrder.verify(textConsole).writeLine(contains("Bob : Good game though"));
        inOrder.verify(textConsole).writeLine(contains("Bob : Damn! We lost!"));
        inOrder.verify(textConsole).writeLine(contains("Alice : I love the weather today"));
    }

    private Optional<User> userWithName(String userName) {
        return userRepository.findUserByName(userName);
    }

    public static Matcher<Optional<User>> hasPostWithMessage(final String message) {
        return new TypeSafeMatcher<Optional<User>>() {

            @Override
            public void describeTo(final Description description) {
                description.appendText("expected result from getPosts() was a post with message: ")
                        .appendValue(message);
            }

            @Override
            protected boolean matchesSafely(final Optional<User> user) {
                if (user.isPresent()) {
                    List<Post> userPosts = user.get().getPosts();
                    for (Post post : userPosts) {
                        if (post.getMessage().contains(message)) {
                            return true;
                        }
                    }
                }

                return false;
            }

            @Override
            public void describeMismatchSafely(final Optional<User> user,
                                               final Description mismatchDescription) {

                if (user.isPresent()) {
                    if (user.get().getPosts().size() > 0) {
                        mismatchDescription.appendText("was not containing any posts");
                    } else {
                        mismatchDescription.appendText("was not containing any matching post messages");
                    }
                } else {
                    mismatchDescription.appendText("was an empty optional user");
                }
            }
        };
    }

    public static <T> Matcher<Optional<T>> isNotEmpty() {
        return new TypeSafeMatcher<Optional<T>>() {

            @Override
            public void describeTo(final Description description) {
                description.appendText("expected result from isEmpty was false: ");
            }

            @Override
            protected boolean matchesSafely(final Optional<T> optional) {
                return (optional.isPresent());
            }

            @Override
            public void describeMismatchSafely(final Optional<T> optional,
                                               final Description mismatchDescription) {

                if (optional.isPresent()) {
                    mismatchDescription.appendText("was ").appendValue("false (it was not empty");
                } else {
                    mismatchDescription.appendText("was ").appendValue("true (it was empty)");
                }
            }
        };
    }
}
