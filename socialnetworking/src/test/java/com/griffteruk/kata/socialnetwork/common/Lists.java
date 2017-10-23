package com.griffteruk.kata.socialnetwork.common;

import java.util.ArrayList;
import java.util.List;

public class Lists {
    public static final List<String> EMPTY_LIST_OF_STRINGS = new ArrayList<>();

    public static boolean stringListHasStringStartingWith(List<String> listOfStrings, String stringToStartWith)
    {
        return listOfStrings.stream().anyMatch(item -> item.startsWith(stringToStartWith));
    }
}
