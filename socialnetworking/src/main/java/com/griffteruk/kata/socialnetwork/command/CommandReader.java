package com.griffteruk.kata.socialnetwork.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandReader {

    public CommandReader()
    {

    }

    Command parse(String commandText)
    {
        Pattern pattern = Pattern.compile("([a-zA-Z0-9 ]+) -> ([a-zA-Z0-9 ]+)");
        Matcher matcher = pattern.matcher(commandText);
        if ( matcher.matches() == true ) {
            return new PostCommand();
        }

        return new EmptyCommand();
    }
}
