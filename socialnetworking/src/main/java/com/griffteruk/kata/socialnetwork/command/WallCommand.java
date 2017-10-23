package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.domain.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by User on 21/10/2017.
 */
public class WallCommand implements Command {


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

            List<Post> postsInterestedIn =
                    combinePosts(
                            user.getPosts(),
                            postsFromUsersFollowedBy(user));

            return postMessagesOrderedByTimeDescending(postsInterestedIn);
        }

        return new ArrayList<>();
    }

    private List<Post> combinePosts(List<Post>... listsOfPosts)
    {
        List<Post> combinedPosts = new ArrayList<>();
        for (List<Post> listOfPosts : listsOfPosts) {
            combinedPosts.addAll(listOfPosts);
        }

        return combinedPosts;
    }

    private List<Post> postsFromUsersFollowedBy(User user ) {
        List<Post> posts = new ArrayList<>();
        for(User followedUser : user.getFollowedUsers()) {
            posts.addAll(followedUser.getPosts());
        }
        return posts;
    }

    private List<String> postMessagesOrderedByTimeDescending(List<Post> posts)
    {
        return posts.stream()
                .sorted(Comparator.comparing(Post::getTimestamp))
                .map(post -> new String(post.getMessage()))
                .collect(Collectors.toList());
    }
}
