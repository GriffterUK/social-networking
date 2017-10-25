package com.griffteruk.kata.socialnetwork.domain;

public class SocialPostFactory implements PostFactory {
    @Override
    public Post createPost(String message) {

        return new SocialPost(message);
    }
}
