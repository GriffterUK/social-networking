package com.griffteruk.kata.socialnetwork.domain;

import java.util.List;

/**
 * Created by User on 22/10/2017.
 */
public interface User {

    void addPost(String message);
    String getName();
    List<String> getPosts();
}
