package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.UserPost;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Lee Griffiths on 21/10/2017.
 */
public class WallCommand implements Command {

    private static final List<String> EMPTY_LIST = new ArrayList<>();

    private UserRepository userRepository;
    private String userName;

    public WallCommand(UserRepository userRepository, String userName) {
        this.userRepository = userRepository;
        this.userName = userName;
    }

    @Override
    public List<String> process() {

        Optional<User> optionalUser = userRepository.findUserByName(userName);
        if ( optionalUser.isPresent() ) {

            User user = optionalUser.get();

            List<UserPost> userPostsInterestedIn =
                combinePosts(
                    userPostsForUser(user),
                    postsFromUsersFollowedBy(user));

            return postMessagesInReverseChronologicalOrder(userPostsInterestedIn);
        }

        return EMPTY_LIST;
    }

    private List<UserPost> userPostsForUser(User user)
    {
       return user.getPosts()
                .stream()
                .map(post -> new UserPost(user, post))
                .collect(Collectors.toList());
    }

    private List<UserPost> postsFromUsersFollowedBy(User user ) {

        return user.getFollowedUsers()
                .stream()
                .map(this::userPostsForUser)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<UserPost> combinePosts(List<UserPost>... listsOfUsersPosts)
    {
        return Arrays.stream(listsOfUsersPosts)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<String> postMessagesInReverseChronologicalOrder(List<UserPost> posts)
    {
        return posts.stream()
                .sorted(Comparator.comparing(
                        (Function<UserPost, LocalDateTime>)
                                x -> x.getPost().getTimestamp() )
                        .reversed())
                .map(UserPost::toString)
                .collect(Collectors.toList());
    }
}
