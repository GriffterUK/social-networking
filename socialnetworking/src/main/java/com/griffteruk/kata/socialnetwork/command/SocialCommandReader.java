package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.repositories.SocialUserRepository;

import java.util.Arrays;
import java.util.List;

public class SocialCommandReader {

    private CommandFactory commandFactory;

    public SocialCommandReader()
    {
        this(
            new SocialCommandFactory(
                new SocialUserRepository()));
    }

    public SocialCommandReader(CommandFactory commandFactory)
    {
        this.commandFactory = commandFactory;
    }

    public Command parse(String commandText)
    {
        List<String> commandArguments = Arrays.asList(commandText.split(" "));

        String user =  elementOrDefault(commandArguments, 0, "");
        String operation = elementOrDefault(commandArguments, 1, "");

        String message = commandArguments.size() >= 3 ?
                joinListOfStringsWithSpaces(
                        subsetOfListStartingFrom(commandArguments, 2))
                : "";

        return commandFactory.createCommand(user, operation, message);
    }

    private List<String> subsetOfListStartingFrom(List<String> list, int startingFrom) {
        return list.subList(startingFrom, list.size() - 1);
    }

    private String joinListOfStringsWithSpaces(List<String> commandArguments) {
        return String.join(" ", commandArguments);
    }

    private String elementOrDefault(List<String> elementList, int index, String defaultIfNotExists)
    {
        return ( elementList.size() > index ) ? elementList.get(index) : defaultIfNotExists;
    }
}
