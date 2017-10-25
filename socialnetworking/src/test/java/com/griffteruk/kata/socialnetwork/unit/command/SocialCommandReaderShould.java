package com.griffteruk.kata.socialnetwork.unit.command;

import com.griffteruk.kata.socialnetwork.command.CommandFactory;
import com.griffteruk.kata.socialnetwork.command.SocialCommandReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * Created by User on 21/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SocialCommandReaderShould {

    private static final String EMPTY_COMMAND = "";

    private static final String NO_USER = "";
    private static final String NO_OPERATION = "";
    private static final String NO_MESSAGE = "";

    private static final String USER_ALICE = "Alice";
    private static final String USER_BOB = "Bob";
    private static final String ALICE_POST_MESSAGE = "I love the weather today";
    private static final String ALICE_POST_MESSAGE_WITH_MULTIPLE_WORDS = "Some Message With More Than One Word";

    private static final String POST_OPERATION = "->";
    private static final String READ_OPERATION = "";
    private static final String FOLLOWS_OPERATION = "follows";
    private static final String WALL_OPERATION = "wall";

    @Mock
    private CommandFactory commandFactory;

    @InjectMocks
    private SocialCommandReader commandReader;

    @Test
    public void handleEmptyCommand()
    {
        commandReader.parse(EMPTY_COMMAND);
        verify(commandFactory).createCommand(NO_USER, NO_OPERATION, NO_MESSAGE);
    }

    @Test
    public void handlePostMessage()
    {
        commandReader.parse("Alice -> I love the weather today");
        verify(commandFactory).createCommand(USER_ALICE, POST_OPERATION, ALICE_POST_MESSAGE);
    }

    @Test
    public void handleReadMessage()
    {
        commandReader.parse("Alice");
        verify(commandFactory).createCommand(USER_ALICE, READ_OPERATION, NO_MESSAGE);
    }

    @Test
    public void handleFollowMessage()
    {
        commandReader.parse("Alice follows Bob");
        verify(commandFactory).createCommand(USER_ALICE, FOLLOWS_OPERATION, USER_BOB);
    }

    @Test
    public void handleWallMessage()
    {
        commandReader.parse("Alice wall");
        verify(commandFactory).createCommand(USER_ALICE, WALL_OPERATION, NO_MESSAGE);
    }

    @Test
    public void handleMultiWordPostRequest()
    {
        commandReader.parse("Alice -> Some Message With More Than One Word");
        verify(commandFactory).createCommand(USER_ALICE, POST_OPERATION, ALICE_POST_MESSAGE_WITH_MULTIPLE_WORDS);
    }
}
