package com.griffteruk.kata.socialnetwork.domain;

import java.time.LocalDateTime;

/**
 * Created by User on 22/10/2017.
 */
public interface Post {

    String getMessage();
    LocalDateTime getTimestamp();
}