package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.PostFactory;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Lee Griffiths on 21/10/2017.
 */
public class PostCommand implements Command {

    private static final List<String> EMPTY_LIST = new ArrayList<>();

    private UserRepository userRepository;
    private PostFactory postFactory;
    private String userName;
    private String message;

    public PostCommand(UserRepository userRepository, PostFactory postFactory, String userName, String message) {
        this.userRepository = userRepository;
        this.postFactory = postFactory;
        this.userName = userName;
        this.message = message;
    }

    @Override
    public List<String> process() {

        createUserIfNotExists(userName);

        User user = userRepository.findUserByName(userName).get();
        user.addPost(createPostWithMessage(this.message));

        return EMPTY_LIST;
    }

    protected void createUserIfNotExists(String userName)
    {
        Optional<User> optionalUser = userRepository.findUserByName(userName);
        if ( optionalUser.isPresent() == false ) {
            userRepository.createUser(userName);
        }
    }

    Post createPostWithMessage(String message)
    {
        return postFactory.createPost(message);
    }

}
