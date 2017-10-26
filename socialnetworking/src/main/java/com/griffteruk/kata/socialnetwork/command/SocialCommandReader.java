package com.griffteruk.kata.socialnetwork.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lee Griffiths on 21/10/2017.
 */
public class SocialCommandReader implements CommandReader {

    private static final String EMPTY_STRING = "";
    private static final Pattern commandPattern =
            Pattern.compile("^\\s*(\\S+)\\s*(->|follows|wall|$)\\s*($|.+)", Pattern.CASE_INSENSITIVE);

    private CommandFactory commandFactory;

    public SocialCommandReader(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public Command parse(String commandText) {

        Matcher patternMatcher = commandPattern.matcher(commandText);

        if (patternMatcher.matches()) {
            return commandFor(
                    matcherGroupValueOrDefault(patternMatcher, 1, EMPTY_STRING),
                    matcherGroupValueOrDefault(patternMatcher, 2, EMPTY_STRING),
                    matcherGroupValueOrDefault(patternMatcher, 3, EMPTY_STRING));
        }

        return commandFor(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
    }

    private String matcherGroupValueOrDefault(Matcher matcher, int groupIndex, String defaultValue) {
        return matcher.groupCount() >= groupIndex ? matcher.group(groupIndex) : defaultValue;
    }

    private Command commandFor(String user, String operation, String message) {
        return commandFactory.createCommand(user, operation, message);
    }
}
