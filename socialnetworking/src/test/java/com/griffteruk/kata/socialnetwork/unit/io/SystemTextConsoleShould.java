package com.griffteruk.kata.socialnetwork.unit.io;

import com.griffteruk.kata.socialnetwork.io.SystemTextConsole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SystemTextConsoleShould {

    private static final String EMPTY_STRING = "";
    private static final String TEXT = "some line";
    private static final String TEXT_WITH_LINE_ENDING = TEXT + "\n";
    private static final String CARET = "> ";

    @Mock
    private PrintStream outputPrintStream;

    private SystemTextConsole systemTextConsole;

    @Test
    public void readLine() {

        InputStream inputStream = new ByteArrayInputStream(TEXT_WITH_LINE_ENDING.getBytes());

        systemTextConsole =
                new SystemTextConsole(inputStream, outputPrintStream);

        assertThat(systemTextConsole.readLine(), is(TEXT));
    }

    @Test
    public void handleIoExceptionAndReturnEmptyString() {
        systemTextConsole =
                new SystemTextConsole(null, null);

        assertThat(systemTextConsole.readLine(), is(EMPTY_STRING));
        systemTextConsole.writeCaret();
        systemTextConsole.writeLine(TEXT);
    }

    @Test
    public void writeLine() {

        systemTextConsole =
                new SystemTextConsole(null, outputPrintStream);

        systemTextConsole.writeLine(TEXT);
        verify(outputPrintStream).println(TEXT);
    }

    @Test
    public void writeCaret() {

        systemTextConsole =
                new SystemTextConsole(null, outputPrintStream);

        systemTextConsole.writeCaret();
        verify(outputPrintStream).print(CARET);
    }

}