package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.UserRepository;

import java.util.Arrays;
import java.util.List;

public class CommandReader {

    private CommandFactory commandFactory;
  
    public CommandReader(UserRepository userRepository)
    {
        // TODO - change this the commandreader should not need to accept a UserRepo
        //        just for the purposes of passing it on
        //        consider making the command factory an interface (and passing it into here)
        this.commandFactory = new CommandFactory(userRepository);
    }

    Command parse(String commandText)
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
