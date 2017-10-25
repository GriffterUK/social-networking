package com.griffteruk.kata.socialnetwork.command;

public interface CommandReader {
    Command parse(String commandText);
}
