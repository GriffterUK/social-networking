package com.griffteruk.kata.socialnetwork.unit.repositories;

import com.griffteruk.kata.socialnetwork.unit.domain.User;

import java.util.Optional;

/**
 * Created by User on 22/10/2017.
 */
public interface UserRepository {

    User createUser(String name);
    Optional<User> findUserByName(String name);
}
