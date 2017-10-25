package com.griffteruk.kata.socialnetwork.domain;

/**
 * Created by User on 25/10/2017.
 */
public class UserPost
{
    private final User user;
    private final Post post;

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

    @Override
    public String toString()
    {
        return getUser().getName() + " : " + getPost().getMessage();
    }
}
