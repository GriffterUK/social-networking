package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.io.TextConsole;

public class CommandReader {

    private TextConsole textConsole;

    public CommandReader(TextConsole textConsole)
    {
        this.textConsole = textConsole;
    }

    public void execute()
    {
        String textInput = textConsole.readLine();
        while ( textInput != "quit") {
            textConsole.readLine();
        }
    }
}
