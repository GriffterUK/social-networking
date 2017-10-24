package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by User on 21/10/2017.
 */
public class ReadCommand implements Command {

    private static List<String> NO_POSTS = new ArrayList<>();

    private UserRepository userRepository;
    private String userName;

    public ReadCommand(UserRepository userRepository, String userName) {
        this.userRepository = userRepository;
        this.userName = userName;
    }

    @Override
    public List<String> process()
    {
        Optional<User> user = userRepository.findUserByName(userName);

        return user.isPresent() ?
                postsForUser(user.get()) : NO_POSTS;
    }

    public List<String> postsForUser(User user) {

        List<String> userPostMessages = new ArrayList<>();
        List<Post> userPosts = user.getPosts();

        for (Post userPost : userPosts) {
            userPostMessages.add(userPost.getMessage());
        }

        return userPosts.stream()
                .map(post -> new String(post.getMessage()))
                .collect(Collectors.toList());
    }
}
