package com.root;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileListTest {

    @Test
    void fileListRecursive() {
        FileList app = new FileList();
        CommandLine cmd = new CommandLine(app);

        StringWriter sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        int exitCode = cmd.execute(".", "-r");
        assertEquals(0, exitCode);
    }

    @Test
    void fileListNotRecursive() {
        FileList app = new FileList();
        CommandLine cmd = new CommandLine(app);

        StringWriter sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        int exitCode = cmd.execute(".");
        assertEquals(0, exitCode);
    }

}
