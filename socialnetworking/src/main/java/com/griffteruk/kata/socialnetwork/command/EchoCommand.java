package com.griffteruk.kata.socialnetwork.command;

import java.util.List;

public class EchoCommand implements Command {

    private List<String> listOfStringsToEcho;

    public EchoCommand(List<String> listOfStringsToEcho)
    {
        this.listOfStringsToEcho = listOfStringsToEcho;
    }

    @Override
    public List<String> process() {
        return listOfStringsToEcho;
    }
}
