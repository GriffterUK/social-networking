package com.griffteruk.kata.socialnetwork.acceptance;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.PostFactory;
import com.griffteruk.kata.socialnetwork.domain.SocialPost;

public class SocialPostWithDelayFactory implements PostFactory {

    private int delayInMilliseconds;

    public SocialPostWithDelayFactory(int delayInMilliSeconds)
    {
        this.delayInMilliseconds = delayInMilliSeconds;
    }

    @Override
    public Post createPost(String message) {
        try {
            Thread.sleep(delayInMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new SocialPost(message);
    }
}
