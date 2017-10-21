package com.griffteruk.kata.socialnetwork.command;

/**
 * Created by User on 21/10/2017.
 */
public class CommandFactory {

    public Command createCommand(String user, String operation, String message)
    {
        Command command = new EmptyCommand();

        switch (operation)
        {
            case "post":
                command = new PostCommand();
                break;
        }

        return command;
    }
}
