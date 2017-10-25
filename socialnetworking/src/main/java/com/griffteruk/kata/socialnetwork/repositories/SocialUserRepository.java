package com.griffteruk.kata.socialnetwork.repositories;

import com.griffteruk.kata.socialnetwork.domain.SocialUser;
import com.griffteruk.kata.socialnetwork.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Lee Griffiths on 22/10/2017.
 */
public class SocialUserRepository implements UserRepository {

    private List<User> users = new ArrayList<>();

    public User createUser(String name)
    {
        Optional<User> existingUser = findUserByName(name);
        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        User user = new SocialUser(name);
        users.add(user);
        return user;
    }

    public Optional<User> findUserByName(String name)
    {
        for (User user : users ) {
            if ( user.getName().compareToIgnoreCase(name) == 0 ) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}
