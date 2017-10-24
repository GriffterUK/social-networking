package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by User on 21/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CommandReaderShould {

    private static final String EMPTY_COMMAND = "";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SocialCommandFactory commandFactory;

    private CommandReader commandReader;

    @Before
    public void initialise()
    {
        commandReader = new CommandReader(commandFactory);
    }

    @Test
    public void returnEmptyCommandWhenRequestIsEmpty()
    {
        assertThat(commandReader.parse(EMPTY_COMMAND),  is(instanceOf(EmptyCommand.class)));
    }

    @Test
    public void returnPostCommandWhenRequestIsPost()
    {
        assertThat(commandReader.parse("Alice -> I love the weather today"), is(instanceOf(PostCommand.class)));
    }

    @Test
    public void returnReadCommandWhenRequestIsRead()
    {
        assertThat(commandReader.parse("Alice"), is(instanceOf(ReadCommand.class)));
    }

    @Test
    public void returnFollowCommandWhenRequestIsFollow()
    {
        assertThat(commandReader.parse("Alice follows Bob"), is(instanceOf(FollowCommand.class)));
    }

    @Test
    public void returnWallCommandWhenRequestIsWall()
    {
        assertThat(commandReader.parse("Alice wall"), is(instanceOf(WallCommand.class)));
    }
}
