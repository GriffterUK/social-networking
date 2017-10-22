package com.griffteruk.kata.socialnetwork.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by User on 22/10/2017.
 */
public class User {

    private String name;
    private List<String> posts = new ArrayList<>();

    public User(String name)
    {
        this.name = name;
    }

    public void addPost(String message)
    {
        this.posts.add(message);
    }

    public String getName() {
        return name;
    }

    public List<String> getPosts()
    {
        return Collections.unmodifiableList(posts);
    }
}
