package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SocialCommandFactoryShould {

    private static final String EMPTY_USER = "";
    private static final String EMPTY_OPERATION = "";
    private static final String EMPTY_MESSAGE = "";

    private static final String SOME_USER = "Alice";
    private static final String ANOTHER_USER = "Bob";

    private static final String POST_OPERATION = "->";
    private static final String POST_MESSAGE = "Hello World";

    private static final String READ_OPERATION = "";

    private static final String FOLLOW_OPERATION = "follows";

    private static final String WALL_OPERATION = "wall";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SocialCommandFactory commandFactory;

    @Test
    public void constructEmptyCommandWhenCreateParametersAreEmpty()
    {
        assertThat(commandFactory.createCommand(EMPTY_USER, EMPTY_OPERATION, EMPTY_MESSAGE),
                is(instanceOf(EmptyCommand.class)));
    }

    @Test
    public void constructPostCommandWhenCreateHasPostParameters()
    {
        assertThat(commandFactory.createCommand(SOME_USER, POST_OPERATION, POST_MESSAGE),
                is(instanceOf(PostCommand.class)));
    }

    @Test
    public void constructReadCommandWhenCreateHasReadParameters()
    {
        assertThat(commandFactory.createCommand(SOME_USER, READ_OPERATION, EMPTY_MESSAGE),
                is(instanceOf(ReadCommand.class)));
    }

    @Test
    public void constructFollowCommandWhenCreateHasFollowParameters()
    {
        assertThat(commandFactory.createCommand(SOME_USER, FOLLOW_OPERATION, ANOTHER_USER),
                is(instanceOf(FollowCommand.class)));
    }

    @Test
    public void constructWallCommandWhenCreateHasWallParameters()
    {
        assertThat(commandFactory.createCommand(SOME_USER, WALL_OPERATION, EMPTY_MESSAGE),
                is(instanceOf(WallCommand.class)));
    }
}
