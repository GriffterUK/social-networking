package com.griffteruk.kata.socialnetwork.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee Griffiths on 21/10/2017.
 */
public class EmptyCommand implements Command {

    private static final List<String> EMPTY_LIST = new ArrayList<>();

    @Override
    public List<String> process() {
        return EMPTY_LIST;
    }
}
