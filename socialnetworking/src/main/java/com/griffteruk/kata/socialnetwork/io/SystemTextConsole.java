package com.griffteruk.kata.socialnetwork.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by User on 25/10/2017.
 */
public class SystemTextConsole implements TextConsole {

    @Override
    public String readLine() {

        BufferedReader systemInBufferedReader =
            new BufferedReader(
                new InputStreamReader(System.in));

        try {
            return systemInBufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void writeLine(String text) {
        System.out.println(text);
    }

    @Override
    public void writeCaret() { System.out.print("> ");}
}
