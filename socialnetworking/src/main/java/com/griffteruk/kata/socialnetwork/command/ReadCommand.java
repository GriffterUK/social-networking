package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Lee Griffiths on 21/10/2017.
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
                postMessagesForUser(user.get()) : NO_POSTS;
    }

    public List<String> postMessagesForUser(User user) {

        return postMessagesInReverseChronologicalOrder(
                user.getPosts());
    }

    protected List<String> postMessagesInReverseChronologicalOrder(List<Post> posts )
    {
        return posts.stream()
                .sorted(Comparator.comparing(Post::getTimestamp).reversed())
                .map(post -> post.getMessage())
                .collect(Collectors.toList());

    }
}
