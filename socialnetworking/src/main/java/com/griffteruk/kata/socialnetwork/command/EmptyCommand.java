package com.griffteruk.kata.socialnetwork.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21/10/2017.
 */
public class EmptyCommand implements Command {

    @Override
    public List<String> process() {
        return new ArrayList<>();
    }
}
