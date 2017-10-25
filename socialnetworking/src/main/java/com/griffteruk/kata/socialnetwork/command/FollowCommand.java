package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Lee Griffiths on 21/10/2017.
 */
public class FollowCommand implements Command {

    private static final List<String> EMPTY_LIST = new ArrayList<>();

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

        Optional<User> user = findUserByName(userName);
        Optional<User> userToFollow = findUserByName(userNameOfUserToFollow);

        if (bothUsersExist(user, userToFollow)) {
            user.get().addUserToFollow(userToFollow.get());
        }

        return EMPTY_LIST;
    }

    private Optional<User> findUserByName(String userName)
    {
        return userRepository.findUserByName(userName);
    }

    private boolean bothUsersExist(Optional<User> someUser, Optional<User> anotherUser) {
        return someUser.isPresent() && anotherUser.isPresent();
    }
}
