package com.griffteruk.kata.socialnetwork.command;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by User on 21/10/2017.
 */
public class PostCommandShould {

    private static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    @Test
    public void returnAnEmptyListAsResult()
    {
        assertThat(new PostCommand().result(), is(EMPTY_LIST_OF_STRINGS));
    }
}
