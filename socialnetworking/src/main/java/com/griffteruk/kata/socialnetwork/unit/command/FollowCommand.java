package com.griffteruk.kata.socialnetwork.unit.command;

import com.griffteruk.kata.socialnetwork.unit.domain.User;
import com.griffteruk.kata.socialnetwork.unit.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by User on 21/10/2017.
 */
public class FollowCommand implements Command {

    private UserRepository userRepository;

    private String userName;
    private String userNameOfUserToFollow;

    public FollowCommand(UserRepository userRepository, String userName, String userNameOfUserToFollow)
    {
        this.userRepository = userRepository;
        this.userName = userName;
        this.userNameOfUserToFollow = userNameOfUserToFollow;
    }

    @Override
    public List<String> process() {

        Optional<User> user = userRepository.findUserByName(userName);
        Optional<User> userToFollow = userRepository.findUserByName(userNameOfUserToFollow);

        if (bothUsersExist(user, userToFollow)) {
            user.get().addUserToFollow(userToFollow.get());
        }

        return new ArrayList<>();
    }

    private boolean bothUsersExist(Optional<User> someUser, Optional<User> anotherUser) {
        return someUser.isPresent() && anotherUser.isPresent();
    }
}
