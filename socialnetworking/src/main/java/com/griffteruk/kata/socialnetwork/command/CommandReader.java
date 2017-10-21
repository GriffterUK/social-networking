package com.griffteruk.kata.socialnetwork.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandReader {

    CommandFactory commandFactory = new CommandFactory();

    public CommandReader()
    {

    }

    Command parse(String commandText)
    {
        String user = "";
        String operation = "";
        String message = "";

        Pattern pattern = Pattern.compile("([a-zA-Z0-9 ]+) -> ([a-zA-Z0-9 ]+)");
        Matcher matcher = pattern.matcher(commandText);

        if ( matcher.find() == true ) {
            operation = "post";
            user = matcher.group(1);
            message = matcher.group(2);
        }

        return commandFactory.createCommand(user, operation, message);
    }
}
