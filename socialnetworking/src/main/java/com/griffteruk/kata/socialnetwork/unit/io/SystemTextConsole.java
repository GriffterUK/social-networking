package com.griffteruk.kata.socialnetwork.unit.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemTextConsole implements TextConsole {

    @Override
    public String readLine() {

        BufferedReader systemInBufferedReader = new BufferedReader(new InputStreamReader(System.in));

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
}
