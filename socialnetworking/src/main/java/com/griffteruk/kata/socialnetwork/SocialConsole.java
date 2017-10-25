package com.griffteruk.kata.socialnetwork;

import com.griffteruk.kata.socialnetwork.command.Command;
import com.griffteruk.kata.socialnetwork.command.CommandReader;
import com.griffteruk.kata.socialnetwork.command.EmptyCommand;
import com.griffteruk.kata.socialnetwork.io.TextConsole;

import java.util.List;

public class SocialConsole {

    private TextConsole textConsole;
    private CommandReader commandReader;

    public SocialConsole(TextConsole textConsole, CommandReader commandReader)
    {
        this.textConsole = textConsole;
        this.commandReader = commandReader;
    }

    public void execute()
    {
        while (true) {

            String userInput = getUserInput();

            Command command = commandReader.parse(userInput);

            if (commandIsInvalidOrEmpty(command)) {
                return;
            }

            writeLinesToConsole(
                command.process()
            );
        }
    }

    protected String getUserInput()
    {
        textConsole.writeCaret();
        return textConsole.readLine();
    }

    protected boolean commandIsInvalidOrEmpty(Command command)
    {
        return ((command == null) || (command instanceof EmptyCommand));
    }

    protected void writeLinesToConsole(List<String> lines)
    {
        for (String line : lines) {
            textConsole.writeLine(line);
        }
    }
}
