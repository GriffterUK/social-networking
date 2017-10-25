package com.griffteruk.kata.socialnetwork.unit.domain;

import com.griffteruk.kata.socialnetwork.domain.Post;
import com.griffteruk.kata.socialnetwork.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockUserBuilder {

    private String name;
    private List<User> usersToFollow = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();

    public static MockUserBuilder aMockUser() {
        return new MockUserBuilder();
    }

    public MockUserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MockUserBuilder followingUser(User user) {
        return followingUsers(user);
    }

    public MockUserBuilder followingUsers(User... users) {
        this.usersToFollow.addAll(Arrays.asList(users));
        return this;
    }

    public MockUserBuilder withPost(Post post) {
        return withPosts(post);
    }

    public MockUserBuilder withPosts(Post... posts) {
        this.posts.addAll(Arrays.asList(posts));
        return this;
    }

    public User build() {
        User user = mock(User.class);
        when(user.getName()).thenReturn(name);
        when(user.getFollowedUsers()).thenReturn(usersToFollow);
        when(user.getPosts()).thenReturn(posts);

        return user;
    }

}
