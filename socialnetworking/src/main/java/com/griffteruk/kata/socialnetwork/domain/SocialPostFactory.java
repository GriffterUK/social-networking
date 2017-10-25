package com.griffteruk.kata.socialnetwork.domain;

/**
 * Created by Lee Griffiths on 22/10/2017.
 */
public class SocialPostFactory implements PostFactory {
    @Override
    public Post createPost(String message) {

        return new SocialPost(message);
    }
}
