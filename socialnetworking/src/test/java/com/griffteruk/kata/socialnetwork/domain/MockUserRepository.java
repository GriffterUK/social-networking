package com.griffteruk.kata.socialnetwork.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockUserRepository {

    private List<User> usersToFind = new ArrayList<>();
    private List<String> userNamesNotToFind = new ArrayList<>();

    public static MockUserRepository aMockUserRepository()
    {
        return new MockUserRepository();
    }

    public MockUserRepository thatDoesNotFindUserName(String userName)
    {
        userNamesNotToFind.add(userName);
        return this;
    }

    public MockUserRepository thatFindsUser(User user)
    {
        usersToFind.add(user);
        return this;
    }

    public UserRepository build()
    {
        UserRepository userRepository = mock(UserRepository.class);
        for (User user : usersToFind) {
            when(userRepository.findUserByName(user.getName())).thenReturn(Optional.of(user));
        }

        for (String userName : userNamesNotToFind) {
            when(userRepository.findUserByName(userName)).thenReturn(Optional.empty());
        }

        return userRepository;
    }

}
