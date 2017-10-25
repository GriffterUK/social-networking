package com.griffteruk.kata.socialnetwork.unit.command;

import com.griffteruk.kata.socialnetwork.command.EmptyCommand;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Lee Griffiths on 21/10/2017.
 */
public class EmptyCommandShould {

    @Test
    public void returnEmptyList()
    {
        EmptyCommand emptyCommand = new EmptyCommand();
        assertTrue(emptyCommand.process().isEmpty());
    }
}
