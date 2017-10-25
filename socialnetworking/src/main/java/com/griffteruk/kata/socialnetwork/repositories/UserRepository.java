package com.griffteruk.kata.socialnetwork.repositories;

import com.griffteruk.kata.socialnetwork.domain.User;

import java.util.Optional;

/**
 * Created by Lee Griffiths on 22/10/2017.
 */
public interface UserRepository {

    User createUser(String name);

    Optional<User> findUserByName(String name);
}
