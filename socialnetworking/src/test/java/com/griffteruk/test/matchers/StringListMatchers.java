package com.griffteruk.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 25/10/2017.
 */
public class StringListMatchers {

    public static Matcher<List<String>> containsInOrderStringsStartingWith(String... items) {
        return new TypeSafeMatcher<List<String>>() {

            @Override
            public void describeTo(final Description description) {
                description.appendText("expected result string list items start with matcher strings in order ");
            }

            @Override
            protected boolean matchesSafely(final List<String> listOfStrings) {

                boolean allItemsMatchInOrder = false;

                List<String> itemsList = Arrays.asList(items);
                if ( listOfStrings.size() >= itemsList.size() ) {

                    int expectedMatches = itemsList.size();
                    int actualMatches = 0;
                    for (int itemIndex = 0; itemIndex < itemsList.size(); itemIndex++) {
                        if ( listOfStrings.get(itemIndex).startsWith(itemsList.get(itemIndex)) ) {
                            actualMatches++;
                        }
                    }

                    if ( actualMatches == expectedMatches ) {
                        allItemsMatchInOrder = true;
                    }
                }

                return allItemsMatchInOrder;
            }

            @Override
            public void describeMismatchSafely(final List<String> listOfStrings,
                                               final Description mismatchDescription) {

                List<String> itemsList = Arrays.asList(items);
                if ( listOfStrings.size() < itemsList.size() ) {
                    mismatchDescription.appendText(" had less items than expected");
                } else {
                    mismatchDescription.appendText(" not matching one or more items");
                }
            }
        };
    }
}
