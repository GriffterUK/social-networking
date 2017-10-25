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
        String textLine = textConsole.readLine();
        while ( !textLine.isEmpty() ) {

            Command command = commandReader.parse(textLine);

            if ( command instanceof EmptyCommand ) {
                break;
            } else {
                writeLinesToConsole(
                    command.process()
                );
            }

            textLine = textConsole.readLine();
        }
    }

    protected void writeLinesToConsole(List<String> lines)
    {
        for (String line : lines) {
            textConsole.writeLine(line);
        }
    }
}
