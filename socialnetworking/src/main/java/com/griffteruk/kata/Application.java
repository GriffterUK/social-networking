package com.griffteruk.kata;

import com.griffteruk.kata.socialnetwork.SocialConsole;
import com.griffteruk.kata.socialnetwork.command.SocialCommandFactory;
import com.griffteruk.kata.socialnetwork.command.SocialCommandReader;
import com.griffteruk.kata.socialnetwork.domain.SocialPostFactory;
import com.griffteruk.kata.socialnetwork.io.SystemTextConsole;
import com.griffteruk.kata.socialnetwork.repositories.SocialUserRepository;

/**
 * NATTER!
 * - the console twitter
 */

public class Application {
    public static void main(String[] args) {
        displayUsage();

        SocialConsole socialConsole = new SocialConsole(
                new SystemTextConsole(),
                new SocialCommandReader(new SocialCommandFactory(
                        new SocialUserRepository(),
                        new SocialPostFactory()))
        );

        socialConsole.execute();

        displayFinale();
    }

    protected static void displayUsage() {
        System.out.println("");
        System.out.println("--------------------------------------------------");
        System.out.println("Hello and Welcome to NATTER (the console twitter)");
        System.out.println("--------------------------------------------------");
        System.out.println("");

        System.out.println("To POST a comment for a user, type:");
        System.out.println("USERNAME -> MESSAGE");
        System.out.println("");

        System.out.println("To READ comments from a user, type:");
        System.out.println("USERNAME");
        System.out.println("");

        System.out.println("To FOLLOW other users, type:");
        System.out.println("USERNAME follows OTHERUSERNAME");
        System.out.println("");

        System.out.println("To SEE THE WALL (posts from a user and users they follow), type:");
        System.out.println("USERNAME wall");
        System.out.println("");

        System.out.println("To QUIT just press enter");
        System.out.println("");
        System.out.println("");
    }

    protected static void displayFinale() {
        System.out.println("");
        System.out.println("");
        System.out.println("Thank You for using NATTER today!");
        System.out.println("");
    }
}
