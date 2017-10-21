package com.griffteruk.kata.socialnetwork.command;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21/10/2017.
 */
public class CommandReaderShould {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();
    private static final String EMPTY_COMMAND = "";
    private static final String NON_EXISTENT_USER = "Alice";

    private CommandReader commandReader;

    @Before
    public void initialise()
    {
        commandReader = new CommandReader();
    }

    @Test
    public void returnEmptyListOfStringsWhenParsingAnEmptyCommand()
    {
        assertThat(resultOfProcessingCommand(EMPTY_COMMAND), is(EMPTY_LIST_OF_STRINGS));
    }

    @Test
    public void returnEmptyListOfStringsWhenRequestingToReadPostsForNonExistentUser()
    {
        assertThat(resultOfProcessingCommand(NON_EXISTENT_USER), is(EMPTY_LIST_OF_STRINGS));
    }

    protected List<String> resultOfProcessingCommand(String commandText) {
        return commandReader.parse(commandText).process();
    }
}
