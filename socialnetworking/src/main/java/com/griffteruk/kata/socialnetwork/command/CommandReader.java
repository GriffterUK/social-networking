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
        String[] commandArguments = commandText.split(" ");

        String user = commandArguments.length >= 1 ? commandArguments[0] : "";
        String operation = commandArguments.length > 2 ? commandArguments[1] : "";
        String message = commandArguments.length >= 3 ? coalesceStrings(commandArguments) : "";  

        return commandFactory.createCommand(user, operation, message);
    }

    private String coalesceStrings(String[] commandArguments) {
        return String.join("", commandArguments);
    }
}
