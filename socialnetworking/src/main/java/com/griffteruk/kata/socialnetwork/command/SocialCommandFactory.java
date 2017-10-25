package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.PostFactory;
import com.griffteruk.kata.socialnetwork.domain.SocialPostFactory;
import com.griffteruk.kata.socialnetwork.repositories.SocialUserRepository;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;

/**
 * Created by User on 21/10/2017.
 */
public class SocialCommandFactory implements CommandFactory {

    private UserRepository userRepository;
    private PostFactory postFactory;

    public SocialCommandFactory()
    {
        this(new SocialUserRepository(),
             new SocialPostFactory());
    }

    public SocialCommandFactory(UserRepository userRepository)
    {
        this(userRepository,
             new SocialPostFactory());
    }

    public SocialCommandFactory(UserRepository userRepository, PostFactory postFactory)
    {
        this.userRepository = userRepository;
        this.postFactory = postFactory;
    }

    public Command createCommand(String userName, String operation, String message)
    {
        Command command = new EmptyCommand();

        switch (operation.toLowerCase())
        {
            case "->":
                command = new PostCommand(userRepository, postFactory, userName, message);
                break;
            case "":
                if ( userName.isEmpty() == false ) {
                    command = new ReadCommand(userRepository, userName);
                }
                break;
            case "follows":
                command = new FollowCommand(userRepository, userName, message);
                break;
            case "wall":
                command = new WallCommand(userRepository, userName);
                break;
        }

        return command;
    }
}
