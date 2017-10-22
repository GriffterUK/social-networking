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
            case "->":
                command = new PostCommand(user, message);
                break;
            case "":
                if ( user.isEmpty() == false ) {
                    command = new ReadCommand();
                }
                break;
            case "follows":
                command = new FollowCommand();
                break;
            case "wall":
                command = new WallCommand();
                break;
        }

        return command;
    }
}
