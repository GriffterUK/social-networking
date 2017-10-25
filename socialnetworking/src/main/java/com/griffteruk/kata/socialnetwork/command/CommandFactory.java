package com.griffteruk.kata.socialnetwork.command;

/**
 * Created by Lee Griffiths on 21/10/2017.
 */
public interface CommandFactory {
    Command createCommand(String userName, String operation, String message);
}
