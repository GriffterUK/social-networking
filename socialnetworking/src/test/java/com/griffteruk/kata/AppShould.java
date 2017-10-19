package com.griffteruk.kata;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by lee.griffiths on 01-Aug-17.
 */
public class AppShould {

    @Before
    public void initialize() {

    }

    @Test
    public void beAwesome() {

        assertThat(true, is(true));
    }
}
