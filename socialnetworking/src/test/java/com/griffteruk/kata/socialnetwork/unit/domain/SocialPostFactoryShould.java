package com.griffteruk.kata.socialnetwork.unit.domain;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.PostFactory;
import com.griffteruk.kata.socialnetwork.domain.SocialPost;
import com.griffteruk.kata.socialnetwork.domain.SocialPostFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SocialPostFactoryShould {

    private static final String POST_MESSAGE = "A Message";

    @Test
    public void createSocialPosts() {
        PostFactory socialPostFactory = new SocialPostFactory();
        Post post = socialPostFactory.createPost(POST_MESSAGE);

        assertThat(post, is(instanceOf(SocialPost.class)));
        assertThat(post.getMessage(), containsString(POST_MESSAGE));
    }
}
