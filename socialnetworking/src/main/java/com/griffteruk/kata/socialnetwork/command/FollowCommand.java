package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21/10/2017.
 */
public class FollowCommand implements Command {

    private UserRepository userRepository;

    private String userName;
    private String followingThisUserName;

    public FollowCommand(UserRepository userRepository, String userName, String followingThisUserName)
    {
        this.userRepository = userRepository;
        this.userName = userName;
        this.followingThisUserName = followingThisUserName;
    }

    @Override
    public List<String> process() {
        return new ArrayList<>();
    }
}
