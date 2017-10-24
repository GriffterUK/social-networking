package com.griffteruk.kata.socialnetwork.command;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by User on 21/10/2017.
 */
public class WallCommand implements Command {

    class UserPost
    {
        private User user;
        private Post post;

        public UserPost(User user, Post post)
        {
            this.user = user;
            this.post = post;
        }

        public User getUser() {
            return user;
        }

        public Post getPost() {
            return post;
        }

        public LocalDateTime getPostTimestamp()
        {
            return post.getTimestamp();
        }
    }

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

            return postMessagesOrderedByTimeDescending(userPostsInterestedIn);
        }

        return new ArrayList<>();
    }

    private List<UserPost> userPostsForUser(User user)
    {
        List<UserPost> userPosts = new ArrayList<>();
        for (Post userPost : user.getPosts()) {
            userPosts.add(new UserPost(user, userPost));
        }

        return userPosts;
    }

    private List<UserPost> combinePosts(List<UserPost>... listsOfUsersPosts)
    {
        List<UserPost> combinedUsersPosts = new ArrayList<>();
        for (List<UserPost> listOfPosts : listsOfUsersPosts) {
            combinedUsersPosts.addAll(listOfPosts);
        }

        return combinedUsersPosts;
    }

    private List<UserPost> postsFromUsersFollowedBy(User user ) {

        List<UserPost> userPosts = new ArrayList<>();
        for(User followedUser : user.getFollowedUsers()) {
            userPosts.addAll(userPostsForUser(followedUser));
        }

        return userPosts;
    }

    private List<String> postMessagesOrderedByTimeDescending(List<UserPost> posts)
    {
        return posts.stream()
                .sorted(Comparator.comparing(UserPost::getPostTimestamp))
                .map(userPost -> new String(userPost.user.getName() + " : " + userPost.post.getMessage()))
                .collect(Collectors.toList());
    }
}
