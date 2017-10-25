package com.griffteruk.kata.socialnetwork.unit.command;

public interface CommandReader {
    Command parse(String commandText);
}
