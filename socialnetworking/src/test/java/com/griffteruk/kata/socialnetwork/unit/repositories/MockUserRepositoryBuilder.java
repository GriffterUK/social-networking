package com.griffteruk.kata.socialnetwork.unit.repositories;

import com.griffteruk.kata.socialnetwork.domain.User;
import com.griffteruk.kata.socialnetwork.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockUserRepositoryBuilder {

    private List<User> usersToFind = new ArrayList<>();
    private List<String> userNamesNotToFind = new ArrayList<>();
    private List<User> usersToCreate = new ArrayList<>();

    public static MockUserRepositoryBuilder aMockUserRepository() {
        return new MockUserRepositoryBuilder();
    }

    public MockUserRepositoryBuilder thatDoesNotFindUserWithName(String userName) {
        userNamesNotToFind.add(userName);
        return this;
    }

    public MockUserRepositoryBuilder thatFindsUser(User user) {
        usersToFind.add(user);
        return this;
    }

    public MockUserRepositoryBuilder thatCreatesUser(User user) {
        usersToCreate.add(user);
        return this;
    }

    public UserRepository build() {
        UserRepository userRepository = mock(UserRepository.class);
        for (User user : usersToFind) {
            when(userRepository.findUserByName(user.getName())).thenReturn(Optional.of(user));
        }

        for (String userName : userNamesNotToFind) {
            when(userRepository.findUserByName(userName)).thenReturn(Optional.empty());
        }

        for (User user : usersToCreate) {
            when(userRepository.createUser(user.getName())).thenReturn(user);

            when(userRepository.findUserByName(user.getName())).
                    thenReturn(Optional.empty()).
                    thenReturn(Optional.of(user));
        }

        return userRepository;
    }

}
