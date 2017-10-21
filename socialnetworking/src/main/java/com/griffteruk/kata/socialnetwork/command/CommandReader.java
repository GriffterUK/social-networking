package com.griffteruk.kata.socialnetwork.command;

public class CommandReader {

    public CommandReader()
    {

    }

    Command parse(String commandText)
    {
        return new EmptyCommand();
    }
}
