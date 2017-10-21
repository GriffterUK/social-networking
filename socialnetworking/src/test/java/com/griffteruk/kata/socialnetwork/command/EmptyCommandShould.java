package com.griffteruk.kata.socialnetwork.command;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21/10/2017.
 */
public class EmptyCommandShould {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    @Test
    public void returnAnEmptyListAsResult()
    {
        assertThat(new EmptyCommand().result(), is(EMPTY_LIST_OF_STRINGS));
    }
}
