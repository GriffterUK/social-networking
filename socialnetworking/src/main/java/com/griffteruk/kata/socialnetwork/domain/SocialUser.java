package com.griffteruk.kata.socialnetwork.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by User on 22/10/2017.
 */

public class SocialUser implements User {

    private String name;
    private List<String> posts = new ArrayList<>();
    private List<User> followedUsers = new ArrayList<>();

    public SocialUser(String name)
    {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addPost(String message)
    {
        this.posts.add(message);
    }

    @Override
    public List<String> getPosts()
    {
        return Collections.unmodifiableList(posts);
    }

    @Override
    public void addUserToFollow(User userToFollow) {

        if (userIsNotMe(userToFollow) &&
            userIsNotAlreadyBeingFollowed(userToFollow)) {
            followedUsers.add(userToFollow);
        }
    }

    @Override
    public List<User> getFollowedUsers() {
        return Collections.unmodifiableList(followedUsers);
    }

    private boolean userIsNotMe(User user)
    {
        return !(user == this);
    }

    private boolean userIsNotAlreadyBeingFollowed(User user) {
        return followedUsers.contains(user) == false;
    }
}

