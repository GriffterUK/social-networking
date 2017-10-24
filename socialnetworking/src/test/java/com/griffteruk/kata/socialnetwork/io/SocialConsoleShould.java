package com.griffteruk.kata.socialnetwork.io;

import com.griffteruk.kata.socialnetwork.SocialConsole;
import com.griffteruk.kata.socialnetwork.command.CommandReader;
import com.griffteruk.kata.socialnetwork.command.EchoCommand;
import com.griffteruk.kata.socialnetwork.command.EmptyCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SocialConsoleShould {

    private static String NO_TEXT = "";

    private static String EMPTY_COMMAND_TEXT = " ";

    private static String ECHO_COMMAND_TEXT = "[ECHO][ECHO]";
    private static String FIRST_LINE_OF_ECHO_COMMAND = "Echo";
    private static String SECOND_LINE_OF_ECHO_COMMAND = "Did anyone hear that?";

    private static int TWICE = 2;

    @Mock
    private TextConsole textConsole;

    @Mock
    private CommandReader commandReader;

    @InjectMocks
    private SocialConsole socialConsole;

    @Test
    public void executeUntilEmptyTextLineIsRead()
    {
        when(textConsole.readLine())
            .thenReturn(NO_TEXT);

        socialConsole.execute();

        verify(textConsole).readLine();
        verify(textConsole, never()).writeLine(any());
    }

    @Test
    public void notWriteLinesForEmptyCommand()
    {
        when(textConsole.readLine())
                .thenReturn(EMPTY_COMMAND_TEXT);

        when(commandReader.parse(EMPTY_COMMAND_TEXT)).
                thenReturn(new EmptyCommand());

        socialConsole.execute();

        verify(textConsole).readLine();
        verify(textConsole, never()).writeLine(any());
    }

    @Test
    public void writeLinesForCommandWithLines()
    {
        when(textConsole.readLine())
                .thenReturn(ECHO_COMMAND_TEXT)
                .thenReturn(NO_TEXT);

        when(commandReader.parse(ECHO_COMMAND_TEXT)).
                thenReturn(new EchoCommand(
                        Arrays.asList(
                            FIRST_LINE_OF_ECHO_COMMAND,
                            SECOND_LINE_OF_ECHO_COMMAND
                        )
                ));

        socialConsole.execute();

        verify(textConsole, times(TWICE)).readLine();
        verify(textConsole).writeLine(FIRST_LINE_OF_ECHO_COMMAND);
        verify(textConsole).writeLine(SECOND_LINE_OF_ECHO_COMMAND);
    }
}
