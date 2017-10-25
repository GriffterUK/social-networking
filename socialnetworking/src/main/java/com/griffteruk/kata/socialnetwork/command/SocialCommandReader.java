package com.griffteruk.kata.socialnetwork.command;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Lee Griffiths on 21/10/2017.
 */
public class SocialCommandReader implements CommandReader {

    private static final String EMPTY_STRING = "";
    private static final String BY_SPACES = " ";

    private CommandFactory commandFactory;

    public SocialCommandReader(CommandFactory commandFactory)
    {
        this.commandFactory = commandFactory;
    }

    public Command parse(String commandText)
    {
        List<String> commandArguments =
            Arrays.asList(
                commandText.split(BY_SPACES));

        String user =  elementOrDefault(commandArguments, 0, EMPTY_STRING);
        String operation = elementOrDefault(commandArguments, 1, EMPTY_STRING);

        String message = commandArguments.size() >= 3 ?
            joinListOfStringsWithSpaces(
                subsetOfListStartingFrom(commandArguments, 2))
            : EMPTY_STRING;

        return commandFor(user, operation, message);
    }

    private List<String> subsetOfListStartingFrom(List<String> list, int startingFrom) {
        return list.subList(startingFrom, list.size());
    }

    private String joinListOfStringsWithSpaces(List<String> commandArguments) {
        return String.join(BY_SPACES, commandArguments);
    }

    private String elementOrDefault(List<String> elementList, int index, String defaultIfNotExists)
    {
        return ( elementList.size() > index ) ? elementList.get(index) : defaultIfNotExists;
    }

    private Command commandFor(String user, String operation, String message)
    {
        return commandFactory.createCommand(user, operation, message);
    }
}
