package com.griffteruk.kata.socialnetwork.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21/10/2017.
 */
public class PostCommand implements Command {


    public PostCommand(String user, String message) {


    }

    @Override
    public List<String> result() {
        return new ArrayList<>();
    }
}
