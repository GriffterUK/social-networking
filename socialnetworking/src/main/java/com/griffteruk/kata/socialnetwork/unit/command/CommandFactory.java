package com.griffteruk.kata.socialnetwork.unit.command;

public interface CommandFactory {
    Command createCommand(String userName, String operation, String message);
}
