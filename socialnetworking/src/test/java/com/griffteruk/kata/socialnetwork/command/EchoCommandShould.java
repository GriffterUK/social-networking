package com.griffteruk.kata.socialnetwork.command;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class EchoCommandShould {

    @Test
    public void returnSameListAsProvided()
    {
        List<String> listOfStringsToEcho =
                Arrays.asList("ONE LINE", "TWO LINE", "THREE LINES");

        EchoCommand echoCommand = new EchoCommand(listOfStringsToEcho);

        assertThat(echoCommand.process(), is(listOfStringsToEcho));
    }
}
