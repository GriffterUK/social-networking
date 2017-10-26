package com.griffteruk.kata.socialnetwork.io;

import java.io.*;

/**
 * Created by Lee Griffiths on 25/10/2017.
 */
public class SystemTextConsole implements TextConsole {

    private InputStream inputStream;
    private PrintStream outputPrintStream;

    public SystemTextConsole() {
        this(System.in, System.out);
    }

    public SystemTextConsole(InputStream inputStream, PrintStream outputPrintStream) {
        this.inputStream = inputStream;
        this.outputPrintStream = outputPrintStream;
    }

    @Override
    public String readLine() {

        try {

            BufferedReader systemInBufferedReader =
                    new BufferedReader(
                            new InputStreamReader(inputStream));

            return systemInBufferedReader.readLine();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void writeLine(String text) {
        if (outputPrintStream != null)
            outputPrintStream.println(text);
    }

    @Override
    public void writeCaret() {
        if (outputPrintStream != null)
            outputPrintStream.print("> ");
    }
}
