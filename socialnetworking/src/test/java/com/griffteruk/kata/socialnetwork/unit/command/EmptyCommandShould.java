package com.griffteruk.kata.socialnetwork.unit.command;

import com.griffteruk.kata.socialnetwork.command.EmptyCommand;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EmptyCommandShould {

    @Test
    public void returnEmptyList()
    {
        EmptyCommand emptyCommand = new EmptyCommand();
        assertTrue(emptyCommand.process().isEmpty());
    }
}
