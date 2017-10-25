package com.griffteruk.kata.socialnetwork.unit.domain;

import com.griffteruk.kata.socialnetwork.domain.Post;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockPostBuilder {

    private String message;
    private LocalDateTime timestamp;

    public static MockPostBuilder aMockPost() {
        return new MockPostBuilder();
    }

    public MockPostBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public MockPostBuilder withTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Post build() {
        Post post = mock(Post.class);
        when(post.getMessage()).thenReturn(message);
        when(post.getTimestamp()).thenReturn(timestamp);

        return post;
    }
}
