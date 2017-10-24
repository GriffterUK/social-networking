package com.griffteruk.kata.socialnetwork.command;

public interface CommandFactory {
    Command createCommand(String userName, String operation, String message);
}
