package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.UserRepository;

/**
 * Created by User on 21/10/2017.
 */
public class CommandFactory {

    private UserRepository userRepository;

    public CommandFactory(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public Command createCommand(String user, String operation, String message)
    {
        Command command = new EmptyCommand();

        switch (operation)
        {
            case "->":
                command = new PostCommand(userRepository, user, message);
                break;
            case "":
                if ( user.isEmpty() == false ) {
                    command = new ReadCommand();
                }
                break;
            case "follows":
                command = new FollowCommand(userRepository, user, message);
                break;
            case "wall":
                command = new WallCommand();
                break;
        }

        return command;
    }
}
