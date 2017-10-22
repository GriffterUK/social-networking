package com.griffteruk.kata.socialnetwork.domain;

import java.util.Optional;

/**
 * Created by User on 22/10/2017.
 */
public interface UserRepository {

    boolean createUser(String name);
    Optional<User> findUserByName(String name);
}
