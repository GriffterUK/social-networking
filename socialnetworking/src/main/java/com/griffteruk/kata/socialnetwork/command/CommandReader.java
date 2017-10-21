package com.griffteruk.kata.socialnetwork.command;

import java.util.Arrays;
import java.util.List;

public class CommandReader {

    CommandFactory commandFactory = new CommandFactory();   
  
    public CommandReader()
    {

    }

    Command parse(String commandText)
    {
        List<String> commandArguments = Arrays.asList(commandText.split(" "));

        String user = commandArguments.size() >= 1 ? commandArguments.get(0) : "";
        String operation = commandArguments.size() >= 2 ? commandArguments.get(1) : "";

        String message = commandArguments.size() >= 3 ?
                joinListOfStringsWithSpaces(subsetOfListStartingFrom(commandArguments, 2)) : "";

        return commandFactory.createCommand(user, operation, message);
    }

    private List<String> subsetOfListStartingFrom(List<String> list, int startingFrom) {
        return list.subList(startingFrom, list.size() - 1);
    }

    private String joinListOfStringsWithSpaces(List<String> commandArguments) {
        return String.join(" ", commandArguments);
    }
}
