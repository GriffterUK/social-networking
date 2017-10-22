package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.domain.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        Optional<User> user = userRepository.findUserByName(userName);
        Optional<User> followingUser = userRepository.findUserByName(followingThisUserName);

        if (bothUsersExist(user, followingUser)) {
            user.get().addUserToFollow(followingUser.get());
        }

        return new ArrayList<>();
    }

    private boolean bothUsersExist(Optional<User> someUser, Optional<User> anotherUser) {
        return someUser.isPresent() && anotherUser.isPresent();
    }
}
