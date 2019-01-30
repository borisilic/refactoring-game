package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import name.fraser.neil.plaintext.diff_match_patch;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SomeTest {

    @Test
    public void true_is_true() throws Exception {
        assertTrue(false);
    }

    @Test
    public void testMain() throws IOException {
        diff_match_patch dmp = new diff_match_patch();
        String[] args = {};
        GameRunner.main(args);

        List<String> master = Files.readAllLines(Paths.get("master.txt"));
        String masterString = String.join("\n", master);

        List<String> test = Files.readAllLines(Paths.get("test.txt"));
        String testString = String.join("\n", test);

        assertEquals(masterString, testString);
    }
}
