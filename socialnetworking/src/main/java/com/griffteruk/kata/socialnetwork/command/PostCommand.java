package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.domain.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by User on 21/10/2017.
 */
public class PostCommand implements Command {

    private UserRepository userRepository;
    private String userName;
    private String message;

    public PostCommand(UserRepository userRepository, String userName, String message) {
        this.userRepository = userRepository;
        this.userName = userName;
        this.message = message;
    }

    @Override
    public List<String> process() {

        Optional<User> optionalUser = userRepository.findUserByName(userName);
        if ( optionalUser.isPresent() == false ) {
            userRepository.createUser(userName);
        }

        return new ArrayList<>();
    }
}
