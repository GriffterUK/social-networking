package com.griffteruk.kata.socialnetwork.domain;

import java.util.List;

/**
 * Created by Lee Griffiths on 22/10/2017.
 */
public interface User {

    String getName();

    void addPost(Post post);

    List<Post> getPosts();

    void addUserToFollow(User user);

    List<User> getFollowedUsers();
}
