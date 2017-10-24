package com.griffteruk.kata;

import com.griffteruk.kata.socialnetwork.SocialConsole;
import com.griffteruk.kata.socialnetwork.command.SocialCommandReader;
import com.griffteruk.kata.socialnetwork.io.SystemTextConsole;

/**
 * Hello world!
 *
 */

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello and Welcome to NATTER (the console twitter)" );
        System.out.println("");

        SocialConsole socialConsole = new SocialConsole(
                new SystemTextConsole(),
                new SocialCommandReader()
        );

        socialConsole.execute();
    }
}
