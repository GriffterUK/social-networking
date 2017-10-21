package com.griffteruk.kata.socialnetwork.command;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by User on 21/10/2017.
 */
public class CommandReaderShould {

    private static final String EMPTY_COMMAND = "";

    private CommandReader commandReader;

    @Before
    public void initialise()
    {
        commandReader = new CommandReader();
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
}
