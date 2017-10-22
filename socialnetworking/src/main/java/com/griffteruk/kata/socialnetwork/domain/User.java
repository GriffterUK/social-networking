package com.griffteruk.kata.socialnetwork.domain;

import java.util.List;

/**
 * Created by User on 22/10/2017.
 */
public interface User {

    String getName();

    void addPost(String message);
    List<String> getPosts();

    void addUserToFollow(User user);
    List<User> getFollowedUsers();
}
